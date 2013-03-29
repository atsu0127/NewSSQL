package supersql.codegenerator.TESTHTML;

import java.io.PrintWriter;
import java.util.Vector;
import supersql.codegenerator.Connector;
import supersql.codegenerator.DecorateList;
import supersql.codegenerator.LocalEnv;
import supersql.codegenerator.TFE;
import supersql.parser.SSQLparser;
import supersql.common.Log;
import supersql.common.GlobalEnv;

public class TESTHTMLEnv extends LocalEnv {
    String data;

    String pre_operator;

    Vector written_classid;

    Connector connector;


    //Vector not_written_classid;
    Vector<String> not_written_classid= new Vector();

    int total_element = 0;

    int glevel = 0;

    String filename;

    String outfile;

    String linkoutfile;

    String nextbackfile = new String();

    String outdir;

    int countfile;

    PrintWriter writer;

    StringBuffer code;

    StringBuffer css;

	static int ID_counter=0;	//add oka

	static int ID_old=0;		//add oka

    //tk start///////////////////////////////////////////////////
    StringBuffer meta = new StringBuffer();
    StringBuffer div = new StringBuffer();
    StringBuffer title = new StringBuffer();
    StringBuffer titleclass = new StringBuffer();
    StringBuffer cssfile = new StringBuffer();
    String tableborder=new String("1");
    boolean embedflag = false;
    int embedcount = 0;

    int haveClass = 0;

    //for ajax
    String ajaxquery = new String();
    String ajaxcond = new String();
    String ajaxatt = new String();
    String ajaxtarget = new String();
    int inEffect = 0;
    int outEffect = 0;
    boolean has_dispdiv = false;

    //for drag
    StringBuffer script = new StringBuffer();
    int scriptnum = 0;
    boolean draggable = false;
    String dragdivid = new String();

    //for panel
    boolean isPanel = false;
    //tk end//////////////////////////////////////////////////////

    StringBuffer header;

    StringBuffer footer;

    boolean foreach_flag;

    boolean sinvoke_flag = false;

    int link_flag;

    String linkurl;

    // ��?�Ѥ�CSS CLASS����?��?
    private String KeisenMode = "";

    public TESTHTMLEnv() {
    }

    public String getEncode(){
    	if(getOs().contains("Windows")){
        	return "Shift_JIS";
    	}else{
    		return "EUC_JP";
    	}
    }

    public String getOs(){
    	String osname = System.getProperty("os.name");
    	return osname;
    }

    public void getHeader() {
   		int index = 0;
        header.insert(index,"<HEAD>\n");
        header.insert(index,"<HTML>\n");
        Log.out("<HTML>");
        Log.out("<head>");

        Log.out("<style type=text/css><!--");

        header.append(cssfile);
        //style��head�˽񤭹���
        header.append("<STYLE TYPE=text/css><!--\n");
        commonCSS();
        header.append(css);
        Log.out(css.toString());

        header.append("--></STYLE>");

        //tk start////////////////////////////////////////////////////
        header.append(meta);

        if(GlobalEnv.isAjax())
        {
        	String js = GlobalEnv.getJsDirectory();
        	if(js != null)
        	{
        		if(js.endsWith("/"))
        			js = js.substring(0,js.lastIndexOf("/"));

	        	header.append("<script src=\""+js+"/prototype.js\" type=\"text/javascript\"></script>\n");
	        	header.append("<script src=\""+js+"/ajax.js\" type=\"text/javascript\"></script>");

        	}
        	else
        	{
	        	header.append("<script src=\"http://localhost:8080/tab/prototype.js\" type=\"text/javascript\"></script>\n");
	        	header.append("<script src=\"http://localhost:8080/tab/ajax.js\" type=\"text/javascript\"></script>");
        	}

            header.append("<script type=\"text/javascript\" src=\"build/yahoo/yahoo-min.js\"></script>\n");
            header.append("<script type=\"text/javascript\" src=\"build/event/event-min.js\" ></script>\n");
            header.append("<script type=\"text/javascript\" src=\"build/dom/dom-min.js\"></script>\n");
            header.append("<script type=\"text/javascript\" src=\"build/dragdrop/dragdrop-min.js\" ></script>\n");
            header.append("<script type=\"text/javascript\" src=\"ssqlajax.js\" ></script>\n");
            header.append("<script type=\"text/javascript\" src=\"prototype.js\" ></script>\n");

            //for tab
            header.append("<script type=\"text/javascript\" src=\"build/element/element-beta.js\"></script>\n");
            header.append("<script type=\"text/javascript\" src=\"build/tabview/tabview.js\"></script>\n");

            //for panel
            header.append("<script type=\"text/javascript\" src=\"build/container/container.js\"></script>\n");

            //for animation
            header.append("<script type=\"text/javascript\" src=\"build/animation/animation.js\"></script>\n");

            //for lightbox
            header.append("<script type=\"text/javascript\" src=\"js/prototype.js\"></script>\n");
            header.append("<script type=\"text/javascript\" src=\"js/scriptaculous.js?load=effects\"></script>\n");
            header.append("<script type=\"text/javascript\" src=\"js/lightbox.js\"></script>\n");

            //for tab css
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"build/tabview/assets/border_tabs.css\">\n");
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"build/tabview/assets/tabview.css\">\n");

            //for panel css
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"build/container/assets/container.css\">\n");
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"build/container/assets/container.css\">\n");

            //for lightbox css
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/lightbox.css\"  media=\"screen\">\n");

            //for custom tab
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/tabview-core.css\"  media=\"screen\">\n");

            //for custom panel
            header.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/panel.css\"  media=\"screen\">\n");

            header.append("<script type=\"text/javascript\">");
            header.append(script);
            header.append("</script>");

        }

        header.append("</HEAD>\n");

        header.append("<BODY class=\"body\">\n");

        header.append("<div");
        header.append(div);
        header.append(titleclass);
        header.append(">");
        header.append(title);
        //tk end///////////////////////////////////////////////////////
        //chie//

        Log.out("--></style></head>");
        Log.out("<body>");

        if(connector.login_flag ){
        	//header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/servlet/supersql.form.Session\" method = \"post\" name=\"theForm\">\n");
        	header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/supersql.form.Session\" method = \"post\" name=\"theForm\">\n");
        	header.append("<input type=\"hidden\" name=\"tableinfo\" value=\"" + SSQLparser.get_from_info_st() + "\" >");
        	header.append("<input type=\"hidden\" name=\"configfile\" value=\"" + GlobalEnv.getconfigfile() + "\" >");
        }

        if(connector.logout_flag ){
        	//header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/servlet/supersql.form.Session\" method = \"post\" name=\"theForm\">\n");
        	header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/supersql.form.Session\" method = \"post\" name=\"theForm\">\n");
        	//header.append("<input type=\"hidden\" name=\"tableinfo\" value=\"" + SSQLparser.get_from_info_st() + "\" >");
        	header.append("<input type=\"hidden\" name=\"configfile\" value=\"" + GlobalEnv.getconfigfile() + "\" >");
        }


        if(connector.insert_flag || connector.delete_flag || connector.update_flag){
        	//header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/servlet/supersql.form.Update\" method = \"post\" name=\"theForm\">\n");
        	header.append("<form action = \""+ GlobalEnv.getFileDirectory() + "/supersql.form.Update\" method = \"post\" name=\"theForm\">\n");
        	header.append("<input type=\"hidden\" name=\"tableinfo\" value=\"" + SSQLparser.get_from_info_st() + "\" >");
        	header.append("<input type=\"hidden\" name=\"configfile\" value=\"" + GlobalEnv.getconfigfile() + "\" >");
        	if(connector.insert_flag)
        		header.append("<input type=\"hidden\" name=\"sql_param\" value=\"insert\" >");
        	if(connector.delete_flag)
        		header.append("<input type=\"hidden\" name=\"sql_param\" value=\"delete\" >");
        	if(connector.update_flag)
            	header.append("<input type=\"hidden\" name=\"sql_param\" value=\"update\" >");
        }

    }

    private void commonCSS() {
        //        header.append("TABLE {border-collapse:collapse; table-layout:fixed; border:none; height:100%;}\n");


    	//tk modified
    	//header.append(".nest { height:100%;z-index: 1}\n");
        //header.append(".nest { position : relative ; top ; 5px ; }");
    	//header.append(".nest {top : 5px ;}");

    	//comment out 200805 chie
    	//header.append(".nest {top : 5px ; vertical-align : top;}");

    	//        header.append(".outline { border: 2px solid gray; height:auto;}\n");
      	//        header.append("TD { padding: 0;}\n");
        //        header.append("TD.tate { border-top: 2px solid gray;}\n");
        //        header.append("TD.top { border-top: none;}\n");
        //        header.append("TD.yoko { border-left: 2px solid gray;}\n");
        //        header.append("TD.left { border-left: none;}\n");
        //tk//
    	if(!GlobalEnv.isOpt()){
        header.append(".att { padding: 0px; margin : 0px;height : 100%; z-index: 2}\n");
        header.append(".linkbutton {text-align:center; margin-top: 5px; padding:5px;}\n");
        header.append(".embed { vertical-align : text-top; padding : 0px ; margin : 0px; border: 0px,0px,0px,0px; width: 100%;}");
        header.append(".noborder { 	border-width : 0px; " +
        		"margin-top : -1px; padding-top : -1px;	" +
        		"margin-bottom : -1px;	padding-bottom : -1px;}");
    	}
    }

    public void getFooter() {
    	if(connector.update_flag || connector.insert_flag|| connector.delete_flag || connector.login_flag ){
	    	footer.append("<input type=\"submit\" name=\"login\" value=\"Let's go!\">");
	    	footer.append("</form>\n");
	    	Log.out("</form>");
        	connector.update_flag = false;
        	connector.insert_flag = false;
        	connector.delete_flag = false;
        	connector.login_flag = false;
    	}

    	if(connector.logout_flag ){
	    	footer.append("</form>\n");
	    	Log.out("</form>");
        	connector.logout_flag = false;
    	}

        footer.append("<BR><BR></BODY></HTML>\n");
        Log.out("</body></html>");
    }

    public void append_css_def_td(String classid, DecorateList decos) {
    	haveClass=0;
    	Log.out("[HTML append_css_def_att] classid=" + classid);
        Log.out("decos = " + decos);

        //��classid�Υ�����?�����Ȥ��������ꤷ�����Ȥ���?��
        if (written_classid.contains(classid)) {
            // �����?�ѤΥ�����?������
        	haveClass=1;
            Log.out("==> already created style");
            return;
        }else if(not_written_classid != null && not_written_classid.contains(classid)){
        	Log.out("==> style is null. not created style");
            return;
        }

        Log.out("==> new style");
        Log.out("@@ creating style no " + classid);

        StringBuffer cssbuf = new StringBuffer();

        //tk start////////////////////////////////////////////////////////////////
        StringBuffer metabuf = new StringBuffer();

        if (decos.containsKey("class")){
        	cssclass.put(classid,decos.getStr("class"));
        	Log.out("class =" + classid + decos.getStr("class"));
        }


        if(decos.containsKey("cssfile")){
        	cssfile.delete(0,cssfile.length());
        	if(GlobalEnv.isServlet()){
            	cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + GlobalEnv.getFileDirectory() + decos.getStr("cssfile") + "\">\n");
            }else{
            	cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + decos.getStr("cssfile") + "\">\n");
            }
        }else if(cssfile.length() == 0){
        	if(GlobalEnv.isServlet()){
            	cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + GlobalEnv.getFileDirectory() +"/default.css \">\n");
            }else{
            	if(getOs().contains("Windows")){
            		cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"default.css\">\n");
            	}else{
            		//itc
            		if(GlobalEnv.isOpt())
            			cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.db.ics.keio.ac.jp/ssqlcss/default_opt.css\">\n");
            		else
            			cssfile.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://www.db.ics.keio.ac.jp/ssqlcss/default.css\">\n");
            	}
            }
        }

        if (decos.containsKey("divalign") && div.length() == 0)
        	div.append(" align=" +decos.getStr("divalign"));

        if (decos.containsKey("title") && title.length() == 0)
        	title.append(decos.getStr("title"));
        if (decos.containsKey("title_class"))
        	titleclass.append(" class=\""+decos.getStr("title_class")+"\"");
        if (decos.containsKey("tableborder") )//&& tableborder.length() == 0)
        	tableborder = decos.getStr("tableborder");

        //tk end//////////////////////////////////////////////////////////////

        // ��??
        if (decos.containsKey("width")) {
            cssbuf.append(" width:" + decos.getStr("width") + ";");
            //        } else {
            //            cssbuf.append(" width:120;");
        }

        // ��??
        if (decos.containsKey("height"))
            cssbuf.append(" height:" + decos.getStr("height") + ";");


     // margin
        if (decos.containsKey("margin")) {
            cssbuf.append(" margin:" + decos.getStr("margin") + ";");
            //        } else {
            //            cssbuf.append(" padding:0.3em;");
        }

        // �ѥǥ��󥰡�;���
        if (decos.containsKey("padding")) {
            cssbuf.append(" padding:" + decos.getStr("padding") + ";");
            //        } else {
            //            cssbuf.append(" padding:0.3em;");
        }
        //padding
        if (decos.containsKey("padding-left")) {
            cssbuf.append(" padding-left:" + decos.getStr("padding-left") + ";");
        }
        if (decos.containsKey("padding-top")) {
            cssbuf.append(" padding-top:" + decos.getStr("padding-top") + ";");
        }
        if (decos.containsKey("padding-right")) {
            cssbuf.append(" padding-right:" + decos.getStr("padding-right") + ";");
        }
        if (decos.containsKey("padding-bottom")) {
            cssbuf.append(" padding-bottom:" + decos.getStr("padding-bottom") + ";");
        }

        // ������
        if (decos.containsKey("align"))
            cssbuf.append(" text-align:" + decos.getStr("align") + ";");

        // �İ���
        if (decos.containsKey("valign"))
            cssbuf.append(" vertical-align:" + decos.getStr("valign") + ";");

        // �طʿ�
        if (decos.containsKey("background-color"))
            cssbuf.append(" background-color:"
                    + decos.getStr("background-color") + ";");
        if (decos.containsKey("bgcolor"))
            cssbuf.append(" background-color:" + decos.getStr("bgcolor") + ";");

        // ʸ��
        if (decos.containsKey("color"))
            cssbuf.append(" color:" + decos.getStr("color") + ";");
        if (decos.containsKey("font-color"))
            cssbuf.append(" color:" + decos.getStr("font-color") + ";");
        if (decos.containsKey("font color"))
            cssbuf.append(" color:" + decos.getStr("font color") + ";");

        // ʸ����
        if (decos.containsKey("font-size"))
            cssbuf.append(" font-size:" + decos.getStr("font-size") + ";");
        if (decos.containsKey("font size"))
            cssbuf.append(" font-size:" + decos.getStr("font size") + ";");
        if (decos.containsKey("size"))
            cssbuf.append(" font-size:" + decos.getStr("size") + ";");

        // ʸ�������
        if (decos.containsKey("font-weight"))
            cssbuf.append(" font-weight:" + decos.getStr("font-weight") + ";");

        // ʸ����?
        if (decos.containsKey("font-style"))
            cssbuf.append(" font-style:" + decos.getStr("font-style") + ";");
        if (decos.containsKey("font-family"))
            cssbuf.append(" font-family:" + decos.getStr("font-family") + ";");

        if(decos.containsKey("border"))
        	cssbuf.append(" border:" + decos.getStr("border")+";");
        if(decos.containsKey("border-width"))
        	cssbuf.append(" border-width:" + decos.getStr("border-width")+";");
        if(decos.containsKey("border-color"))
        	cssbuf.append(" border-color:" + decos.getStr("border-color")+";");
        if(decos.containsKey("border-style"))
        	cssbuf.append(" border-style:" + decos.getStr("border-style")+";");
        if(decos.containsKey("border-collapse"))
        	cssbuf.append(" border-collapse:" + decos.getStr("border-collapse")+";");

        //tk start////////////////////////////////////////////////////////////////
        if (decos.containsKey("charset"))
            metabuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + decos.getStr("charset") + "\">");
 //      else
 //       	metabuf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=euc-jp\">");

        if (decos.containsKey("description"))
            metabuf.append("<meta name=\"Description\" content=\"" + decos.getStr("description") + "\">");
        if (decos.containsKey("keyword"))
            metabuf.append("<meta name=\"Keyword\" content=\"" + decos.getStr("keyword") + "\">");
        if (decos.containsKey("author"))
            metabuf.append("<meta name=\"Author\" content=\"" + decos.getStr("author") + "\">");
        if (decos.containsKey("pragma"))
            metabuf.append("<meta http-equiv=\"Pragma\" content=\"" + decos.getStr("pragma") + "\">");
        if (decos.containsKey("robot"))
            metabuf.append("<meta name=\"Robot\" content=\"" + decos.getStr("robot") + "\">");
        //tk end///////////////////////////////////////////////////////////////////

        if (cssbuf.length() > 0) {
        	haveClass = 1;
            //����?�Υ�����?����
            css.append("." + classid + "{");

            css.append(cssbuf);
            //��?�Υ�����?�Ĥ�
            css.append(" }\n");

            //������?��?�Ѥߥ��饹��id����¸���Ƥ���
            written_classid.addElement(classid);
        }else{
        	Log.out("==> style is null. not created style");
        	not_written_classid.addElement(classid);
        }

        //tk start//////////////////////////////////////////////////////////
        if(metabuf.length() > 0)
        {
            meta.append(" ");
            meta.append(metabuf);
         	meta.append("\n");

        }
       //tk end////////////////////////////////////////////////////////////


    }

    // outline����Ϥ�?���ɤ����Υե饰��?
    boolean OutlineMode = false;

    public void setOutlineMode() {
        OutlineMode = true;
    }

    public String getOutlineMode() {
        if (OutlineMode) {
            OutlineMode = false;
            return "";
        }
//        return " frame=void class=nest ";
      return " frame=void ";
    }

    public String getOutlineModeAtt() {
        if (OutlineMode) {
            OutlineMode = false;
            return " outline";
        }
        return "";
    }

    public static String getClassID(TFE tfe) {
    	String result;
        if (tfe instanceof TESTHTMLC3) {
            result = getClassID(((TFE) ((TESTHTMLC3) tfe).tfes.get(0)));
            return result;
        }
        if (tfe instanceof TESTHTMLG3) {
            result = getClassID(((TFE) ((TESTHTMLG3) tfe).tfe));
            	return result;
        }
        result =  "TFE" + tfe.getId();
        	return result;
    }

    /***start oka***/
    public static String getDataID(TFE tfe) {
    	String ClassID;
    	int DataID = 0;
    	String return_value;

        if (tfe instanceof TESTHTMLC3) {
            return getClassID(((TFE) ((TESTHTMLC3) tfe).tfes.get(0)));
        }
        if (tfe instanceof TESTHTMLG3) {
            return getClassID(((TFE) ((TESTHTMLG3) tfe).tfe));
        }
        ClassID = String.valueOf(tfe.getId());
        DataID = Integer.valueOf((ClassID.substring(ClassID.length()-3,ClassID.length()))).intValue();

        Log.out("ClassID="+ClassID);
        Log.out("DataID="+DataID);
        Log.out("ID_counter="+ID_counter);

        if(DataID < ID_old){
        	ID_counter = DataID;
        }
        else{
        	if(DataID != ID_counter && DataID > ID_counter){
        		DataID = ID_counter;
        	}
        }
        ID_counter++;
        ID_old = DataID;
        return_value = String.valueOf(DataID);
        return return_value;
    }


    /********  form method  ************/
    /********** 2009 chie **************/

    //
    public static void initAllFormFlg(){
    	setFormItemFlg(false,null);
    	setSelectFlg(false);
    	setSelectRepeat(false);
    	setFormValueString(null);
    	setFormPartsName(null);
    	setSelected("");
    	setIDU("");
    	form_parts_number = 1;
    	exchange_form_name = new String();
    	form_detail = new String[256];
    	form_number = 1;
    	nameId = "";
    	search = false;
    	searchid = 0;
    	cond_name="";
    	cond ="";
    }


    static boolean isFormItem;
    static String formItemName;
    //form tag is written : true
    public static void setFormItemFlg(boolean b,String s){
    	isFormItem = b;
    	formItemName = s;
    	return;
    }

    public static boolean getFormItemFlg(){
        return isFormItem;
    }

    public static String getFormItemName(){
    	if(formItemName == null){
    		return "0";
    	}
    	return formItemName;
    }

	static boolean select_flg;
	//function select flg -> in func_select true

	//set and get select_flg
	public static void setSelectFlg(boolean b){
		select_flg = b;
	}

	public static boolean getSelectFlg(){
		return select_flg;
	}


	static String formValueString;
	public static void setFormValueString(String s){
		formValueString = s;
	}
	public static String getFormValueString(){
		return formValueString;
	}



	static boolean select_repeat;
	//select_repeat flag
	//not write "<tr><td>" between "<option>"s
	//set and get select_repeat
	public static void setSelectRepeat(boolean b){
		select_repeat = b;
	}
	public static boolean getSelectRepeat(){
		return select_repeat;
	}

	//global form item number : t1,t2,t3...
	static int form_parts_number = 1;
	static String form_parts_name = null;
	public static String getFormPartsName(){
		if(form_parts_name == null){
			return "t"+form_parts_number;
		}else{
			return form_parts_name;
		}
	}
	public static void incrementFormPartsNumber(){
		form_parts_number++;
	}


	public static void setFormPartsName(String s){
		form_parts_name = s;
	}

	private static String exchange_form_name = new String();
	public static void exFormName(){
		String s = "t" + form_parts_number + ":" + form_parts_name +":";
		if(exchange_form_name == null || exchange_form_name.isEmpty()){
			exchange_form_name = ":"+s;
		}else{
			if(!exchange_form_name.contains(s))
				exchange_form_name += s;
		}
	}
	public static String exFormNameCreate(){
		String ret = new String();
		if(exchange_form_name != null){
			ret = "<input type=\"hidden\" name=\"exchangeName\" value=\""+exchange_form_name+"\" />";
			setFormDetail(ret);
			return ret;
		}else{
			return null;
		}
	}



	//global form number : 1,2,3...
	static int form_number = 1;
	public static void incrementFormNumber(){
		form_number++;
	}

	public static int getFormNumber(){
		//return formNumber 1,2,3...
		return form_number;
	}
	public static String getFormName(){
		//return formNumber f1,f2,f3...
		return "f"+form_number;
	}

	static String[] form_detail = new String[256];
	public static void setFormDetail(String s){
		if(form_detail[form_number] == null)
			form_detail[form_number] = s;
		else
			form_detail[form_number] += s;
	}
	public static String getFormDetail(int i){
		return form_detail[i];
	}

	static String IDUst = new String();
	public static void setIDU(String s){
		IDUst = s;
	}

	public static String getIDU(){
		return IDUst;
	}

	static String selected = "";

	public static void setSelected(String s){
		selected = s;
	}
	public static String getSelected(){
		return selected;
	}

	static String nameId = "";
	public static String getNameid(){
		if(nameId != null){
			return nameId;
		}else{
			return "";
		}
	}

	static String checked = "";

	public static void setChecked(String s){
		System.out.println("checked:"+s);
		checked = s;
	}
	public static String getChecked(){
		return checked;
	}

	static boolean search = false;
	static int searchid = 0;
	static String cond_name = "";
	static String cond = "";

	public static void setSearch(boolean b){
		search = b;
		searchid = 0;
	}
	public static boolean getSearch(){
		return search;
	}

}