package supersql.dataconstructor;

import java.util.*;

import supersql.codegenerator.AttributeItem;
import supersql.common.GlobalEnv;
import supersql.common.Log;
import supersql.extendclass.ExtList;
import supersql.extendclass.QueryBuffer;
import supersql.parser.FromInfo;
import supersql.parser.FromParse;
import supersql.parser.Preprocessor;
import supersql.parser.Start_Parse;
//ryuryu
import supersql.parser.WhereInfo;
import supersql.parser.WhereParse;

public class MakeSQL {

	//test
	private FromInfo from;

	private WhereInfo where;

	private Hashtable atts;

	private ExtList table_group;

	private ArrayList<Integer> unusedAtts = new ArrayList<>();

	public MakeSQL(Start_Parse p) {
		setFrom(p.get_from_info());
		where = p.whereInfo;
		atts = p.get_att_info();
		Enumeration keys = atts.keys();
		while(keys.hasMoreElements()){
			Object key = keys.nextElement();
			unusedAtts.add((int)key);
		}
		MakeGroup mg = new MakeGroup(atts, where);
		table_group = mg.getTblGroup();
		Log.out("[MakeSQL:table_group]" + table_group);

	}

	public String makeSQL(ExtList sep_sch) {


		boolean flag;
		int i, idx;
		Integer itemno;
		ExtList schf;
		Log.out("[sep_sch]=" + sep_sch);
		schf = sep_sch.unnest();
		Log.out("[schf]" + schf);
		//			StringBuffer buf = new StringBuffer("SELECT ");
		//hanki start
		StringBuffer buf;

		if (Preprocessor.isAggregate()) {
			buf = new StringBuffer("SELECT ALL ");
		} 
		{
			if(Start_Parse.distinct){
				buf = new StringBuffer("SELECT DISTINCT ");
			}
			else{
				buf = new StringBuffer("SELECT ");
			}
			//ryuryu(end)/////////////////////////////////////////////////////
		}
		//hanki end

		int tmp_flag = 0; //ryuryu
//		Log.out("atts::"+atts);
		HashSet tg1 = new HashSet();
		//SELECT句に属性追加
		for (idx = 0; idx < schf.size(); idx++) {
			itemno = (Integer) (schf.get(idx));
			AttributeItem att1 = (AttributeItem) (atts.get(itemno));

			//ryuryu
			/*if (idx != 0) {
				buf.append(", " + att1.getSQLimage());
			} else {
				buf.append(att1.getSQLimage());
			}*/

			//ryuryu(start)//////////////////////////////////////////////////////////////////////////////////////////
			if (idx != 0) {
				buf.append(", " + att1.getSQLimage());
			}
			else{
				buf.append(att1.getSQLimage());
			}


			////			else if(SSQLparser.xpathExist == 1){
			////
			////				if (idx != 0) {
			////					if(att1.getSQLimage().equals(SSQLparser.tmpXpath1)){
			////						buf.append(", " + SSQLparser.Xpath.replace("\"", "'"));
			////						tmp_flag = 1;
			////					}
			////
			////					else if(att1.getSQLimage().equals(SSQLparser.tmpXmlQuery1)){
			////						String tmp_xmlquery = new String();
			////						tmp_xmlquery = (SSQLparser.DB2_XQUERY.replace("\"", "'")).replace((SSQLparser.tmpXmlQuery2 + "',"), (SSQLparser.tmpXmlQuery2 + "' PASSING "));
			////						tmp_xmlquery = tmp_xmlquery.replace((SSQLparser.tmpXmlQuery1 + ")"), (SSQLparser.tmpXmlQuery1 + " AS \"a\")"));
			////						buf.append(", " + tmp_xmlquery);
			////						tmp_flag = 1;
			////					}
			////
			////					else{
			////						if(tmp_flag==0){
			////							buf.append(", " + att1.getSQLimage());
			////						}
			////						else{
			////							buf.append(" " + att1.getSQLimage());
			////							tmp_flag=0;
			////						}
			////					}
			////				}
			//
			//				else {
			//					if(att1.getSQLimage().equals(SSQLparser.tmpXpath1)){
			//						String tmp = SSQLparser.Xpath.replace("\"", "'");
			//						tmp = tmp.replace("),", ")");
			//						buf.append(tmp);
			//
			//						XMLFunction.xpath_first = 1;
			//					}
			//
			//					else if(att1.getSQLimage().equals(SSQLparser.tmpXmlQuery1)){
			//						String tmp_xmlquery = new String();
			//						tmp_xmlquery = (SSQLparser.DB2_XQUERY.replace("\"", "'")).replace((SSQLparser.tmpXmlQuery2 + "',"), (SSQLparser.tmpXmlQuery2 + "' PASSING "));
			//						tmp_xmlquery = tmp_xmlquery.replace((SSQLparser.tmpXmlQuery1 + ")"), (SSQLparser.tmpXmlQuery1 + " AS \"a\")"));
			//						buf.append(tmp_xmlquery);
			//
			//						XMLFunction.xpath_first = 1;
			//					}
			//
			//					else{
			//						buf.append(att1.getSQLimage());
			//					}
			//				}
			//			}
			//ryuryu(end)//////////////////////////////////////////////////////////////////////////////////////////
			
			for (int j = 0; j < table_group.size(); j++) {
//				Log.out("att1::"+att1.getUseTables());
//				Log.out("able_group::"+table_group.get(j));
//				Log.out("tg1::"+tg1);
				if (((HashSet) (table_group.get(j))).containsAll(att1.getUseTables())) {
					tg1.addAll((HashSet) table_group.get(j));
				}
			}
		}
		Log.out("[tg1]" + tg1);

		// From
		flag = false;

		buf.append(" FROM ");

		//Iterator it = tg1.iterator();		//changed by goto 20120523

		Log.out("FROM_INFO:" + getFrom());

		//tk to use outer join////
		try{
			//changed by goto 20120523 start
			//���܂ł�SSQL�ł́A��ӂȑ����̏ꍇ�ł����Ă��K���������̑O��
			//�u�e�[�u����.�v��t����(qualify����)�K�v��������
			//���L�̕ύX�ɂ��A���̖������P����
			//�i����ɂ��A�ʏ��SQL���l�A���j�[�N�ȗ񖼂̑O�ɂ�qualification�͕s�v�ƂȂ�j

			buf.append(((FromParse) getFrom().getFromTable().get("")).getLine());
			/*while (it.hasNext()) {
				String tbl = (String) it.next();
				tbl = (String) it.next();

				Log.out("tbl:"+tbl);
				Log.out("buf@Make:"+ buf);

				if (flag) {
					buf.append(", "
						+ ((FromParse) from.getFromTable().get(tbl)).getLine());
				} else {
					flag = true;
					buf.append(((FromParse) from.getFromTable().get(tbl)).getLine());
				}
			}*/
			//changed by goto 20120523 end

			//tk to use outer join//////////////
		}catch(NullPointerException e){
//			buf.append(getFrom().getLine());
			//add tbt 180711
			//not to use unused table in from clause
			String fClauseBefore = getFrom().getLine();
			String fClauseAfter = new String();
			for (String tb: fClauseBefore.split(",")) {
				String tAlias = tb.split(" ")[1];
				if(tg1.contains(tAlias)){
					fClauseAfter += tb;
					fClauseAfter += ",";
				}
			}
			if(fClauseAfter.charAt(fClauseAfter.length() - 1) == ','){
				fClauseAfter = fClauseAfter.substring(0, fClauseAfter.length() - 1);
			}
			buf.append(fClauseAfter);
		}
		//tk/////////////

		// Where
		flag = false;
		Iterator e2 = where.getWhereClause().iterator();
		while (e2.hasNext()) {
			WhereParse whe = (WhereParse) e2.next();
//			Log.out("whe::"+whe);
			if (tg1.containsAll(whe.getUseTables())) {
				if (flag) {
					buf.append(" AND " + whe.getLine());
//					Log.info(buf.toString());
				} else {
					flag = true;
					buf.append(" WHERE " + whe.getLine());
//					Log.info(buf.toString());
				}
			}
		}

		// buf.append(" limit 5 offset 0"); //todo if infinite-scroll flag = true, add this to sql query.
		if (! GlobalEnv.getdbms().equals("db2")){
			buf.append(";");
		}

		return buf.toString();

	}

	//tbt make 180601
	//for divisions of a SQL query depends on aggregates
	private ArrayList<ArrayList<Integer>> dim = new ArrayList();


	public boolean remainUnUsedAtts(){
		return (unusedAtts.size() > 0);
	}

	//make multiple queries depends on aggregation
	public ArrayList<QueryBuffer> makeMultipleSQL(ExtList sep_sch){
		long beforeMakeMultipleSQL_Tree = System.currentTimeMillis();
		dim.clear();
		ExtList agg_list = Preprocessor.getAggregateList(); //get aggrigate list
		ArrayList<Integer> agg_nums = new ArrayList<>(); //to contain only the index of attributes which is aggregated(not aggregate type name)
		for(Object agg: agg_list){
			agg_nums.add(Integer.parseInt(agg.toString().split(" ")[0]));
		}
		Hashtable<ExtList, ExtList> depend_list = new Hashtable<>();
		//make dependency list of each attributes
		makeDim((ExtList)sep_sch.get(0), 0);
		ExtList dim_all = new ExtList();
		ExtList agg_set = new ExtList();
		//See from the top dimension of dim list
		//We make dependency list based on aggregation.
		//e.g.
		//if sep_sch is [0, 1, [[2], 3, 4], 5] and dim = [[0, 1, 5], [3, 4], [2]]. aggregate is 2 and 4.
		//we make depend_list = {[2]=[0, 1, 5, 3, 4], [4]=[0, 1, 5, 3]}
		for(ArrayList<Integer> d: dim){
			for(int d_num: d){
				if(!agg_nums.contains(d_num)){
					//dim_all contains attribute numbers that is NOT to be aggregated.
					dim_all.add(d_num);
				}
			}
			boolean flag = false;
			ExtList agg_n = new ExtList();
			for(int agg_num: agg_nums){
				//同階層だったら一緒にまとめる
				//if there are aggregate numbers which is in same dimension, we group these numbers
				if(d.contains(agg_num)){
					agg_n.add(agg_num);
					flag = true;
				}
			}
			if(flag){
				//if there are aggregate in this dimension, we add dependency to depend_list.
				agg_set.add(agg_n);
				ExtList tmp = new ExtList();
				for(Object o: dim_all){
					tmp.add(o);
				}
				depend_list.put(agg_n, tmp);
			}
		}
		//make query buffer. the numbers of qb is agg_set.size()
		ArrayList<QueryBuffer> qbs = new ArrayList<>();
		String from_line = getFrom().getLine();
		Hashtable table_alias = new Hashtable();
		//table_alias is hashtable like {table_alias=table_name, ...}
		for(String f:from_line.split(",")){
			table_alias.put(f.trim().split(" ")[1], f.trim().split(" ")[0]);
		}
		ArrayList<String> usedAtts = new ArrayList<>();
		boolean noagg = true;
		for(int i = 0; i < agg_set.size(); i++){
			noagg = false;
			long beforeMakeMultipleSQL_One = System.currentTimeMillis();
			QueryBuffer qb;
			ExtList sep_sch_tmp = new ExtList();
			Object t = agg_set.get(i);
			int num = ((ExtList)t).size();
			//sep_sch_tmp contains use attributes.
			if(num > 1){
				//if the number of aggregation is more than 2.
				for(int l = 0; l < num; l++){
					sep_sch_tmp.add(((ExtList) t).get(l));
				}
			}else {
				sep_sch_tmp.add(((ExtList)t).get(0));
			}
			for(Object o: depend_list.get(agg_set.get(i))){
				sep_sch_tmp.add(o);
			}
			//remove attribute number from unusedAtts.
			for(Object o: sep_sch_tmp){
				int key = (int)o;
				if(unusedAtts.contains(key)){
					unusedAtts.remove(unusedAtts.indexOf(key));
				}
			}
			//set sep_sch to qb
			qb = new QueryBuffer(sep_sch_tmp);
			Hashtable att_tmp = new Hashtable();
			ExtList att_list = new ExtList();
			//make att_tmp and att_list.
			//att_tmp is a set of attribute number and attribute name.
			//att_list is a list of attribute name.
			for(Object attnum: sep_sch_tmp){
				att_tmp.put(attnum, atts.get(attnum));
				att_list.add(((AttributeItem)atts.get(attnum)).getSQLimage());
			}

			//set att_tmp to qb
			qb.setAtts(att_tmp);
			HashSet tg = new HashSet();
			//make tg. tg is a list of using table aliases.
			for(Object o: att_list){
				if(!tg.contains(o.toString().split("\\.")[0])) {
					tg.add(o.toString().split("\\.")[0]);
				}
			}
			//set tg to qb
			qb.setTg(tg);
			String from = getFrom().getLine();
//			int j = 0;
			//make from clause depends on tg
			//I thought this process should be done in QueryBuffer.java, after I implemented orz.
			//180711 Fixed
//			for(Object o: tg){
//				String table = table_alias.get(String.valueOf(o)).toString();
//				if(j == 0) {
//					from_tmp = table + " " + o.toString();
//					j++;
//				}else{
//					from_tmp += ", " + table + " " + o.toString();
//				}
//			}
			qb.setFromInfo(from);
			ExtList agg_tmp = new ExtList();
			//make aggregation list (e.g.[2 count]) which will be used.
			for(Object o: agg_list){
				for(Object p: (ExtList)t){
					if(o.toString().indexOf(p.toString()) != -1){
						agg_tmp.add(o);
						break;
					}
				}
			}
			//set aggregation list to qb.
			qb.setAggregate_list(agg_tmp);
			//set aggregation attribute num to qb.
			qb.setAggregate_attnum_list((ExtList)t);
			//make query by qb
			qb.makeQuery(where);
			long aftereMakeMultipleSQL_One = System.currentTimeMillis();
			System.out.println();
			Log.info("Make One SQL Time : " + (aftereMakeMultipleSQL_One - beforeMakeMultipleSQL_One) + "ms");
			Log.info("Query is : " + qb.getQuery());
			qbs.add(qb);
		}
		if(!noagg) {
			long afterMakeMultipleSQL_Tree = System.currentTimeMillis();
			System.out.println();
			Log.info("Make One Tree SQL Time : " + (afterMakeMultipleSQL_Tree - beforeMakeMultipleSQL_Tree) + "ms");
		}
		return qbs;
	}

	//make dimensions about query dependency
	//[0, 1, [[2], 3, 4], 5] -> [[0, 1, 5], [3, 4], [2]]
	public void makeDim(ExtList sep_sch_m, int idx){
		for(int i = 0; i < sep_sch_m.size(); i++){
			if(sep_sch_m.get(i) instanceof ExtList){
				//if the child is extlist, increment idx and recursive call
				idx++;
				makeDim((ExtList)sep_sch_m.get(i), idx);
				idx--;
			}else{
				try {
					//if there are already exist list corresponding to idx, add attribute number
					dim.get(idx).add((Integer) sep_sch_m.get(i));
				}catch(IndexOutOfBoundsException e){
					//if there are NOT, to contain attributes make empty lists and add to dim
					//e.g.
					//idx = 2 and dim is [[1]]
					//dim become [[0, 1], [], []]
					int sub = idx - dim.size();
					for (int j = 0; j <= sub; j++) {
						ArrayList<Integer> tmp = new ArrayList<>();
						dim.add(tmp);
					}
					//and add
					//dim become [[0, 1], [], [2]]
					dim.get(idx).add((Integer) sep_sch_m.get(i));
				}
			}
		}

	}

	public void copySepSch(ExtList src, ExtList dist){
		for(int i = 0; i < src.size(); i++) {
			try {
				ExtList child = (ExtList)src.get(i);
				ExtList tmp = new ExtList();
				dist.add(tmp);
				copySepSch(child, (ExtList)dist.get(i));
			}catch (ClassCastException e){
				dist.add(i, src.get(i));

			}
		}
	}

	//make query for remaining attributes.
	//e.g. [A, [B, sum[C], D, [E]]] E will not be used in makeMultipleSQL
	public ArrayList<QueryBuffer> makeRemainSQL(ExtList sep_sch) {
		System.out.println();
		ExtList tmp_sep_sch = new ExtList();
		copySepSch(sep_sch, tmp_sep_sch);
		ExtList sep_sch_l = (ExtList)tmp_sep_sch.clone();
		ArrayList<String> queries = new ArrayList<>();
		ArrayList<Integer> aggregateNumber = new ArrayList<>();
		ArrayList<QueryBuffer> qbs = new ArrayList<>();
		for(Object o: Preprocessor.getAggregateList()){
			aggregateNumber.add(Integer.parseInt(o.toString().split(" ")[0]));
		}
		for(int a = 0; a < sep_sch_l.size(); a++){
			//each tree in sep_sch_l
			ExtList tmp_l = new ExtList();
			tmp_l = (ExtList)((ExtList)sep_sch_l.get(a)).clone();
			ExtList o = new ExtList(tmp_l);
			ExtList tree = o.unnest();
			for(int j = 0; j < unusedAtts.size(); j++){
				int i = unusedAtts.get(j);
				//if tree contains unused attributes
				if(tree.contains(i)){
					long beforeMakeOneRemainSQL = System.currentTimeMillis();
					for(int k: aggregateNumber){
						//remove aggregate number
						o.removeContent(k);
					}
					//make SQL by default method
					QueryBuffer qb = new QueryBuffer((ExtList)o);
					qb.setQuery(makeSQL((ExtList)o));
					System.out.println();
					Log.info("Query is : " + qb.getQuery());
					qbs.add(qb);
					//remove attribute numbers from unusedAtts
					for(Object b: tree){
						int key = (int)b;
						if(unusedAtts.contains(key)){
							unusedAtts.remove(unusedAtts.indexOf(key));
						}
					}
					long afterMakeOneRemainSQL = System.currentTimeMillis();
					Log.info("Make One Remain SQL Time : " + (afterMakeOneRemainSQL - beforeMakeOneRemainSQL) + "ms");
				}

			}
		}
		return qbs;
	}
	////tbt end

	public FromInfo getFrom() {
		return from;
	}

	public void setFrom(FromInfo fromInfo) {
		this.from = fromInfo;
	}
}