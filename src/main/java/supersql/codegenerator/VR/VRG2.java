package supersql.codegenerator.VR;

import supersql.codegenerator.Grouper;
import supersql.codegenerator.Manager;
import supersql.common.GlobalEnv;
import supersql.common.Log;
import supersql.extendclass.ExtList;

public class VRG2 extends Grouper {

	private VREnv vr_env;
	private VREnv vr_env2;
	boolean retFlag = false;	// 20140611_masato pagenationフラグ
	boolean pageFlag = false;	// 20140611_masato pagenationフラグ

	public VRG2(Manager manager, VREnv henv, VREnv henv2) {
		this.vr_env = henv;
		this.vr_env2 = henv2;
	}

	@Override
	public String getSymbol() {
		return "VRG2";
	}

	@Override
	public String work(ExtList data_info) {

		Log.out("------- G2 -------");
		this.setDataList(data_info);
		if (VREnv.getSelectFlg()){
			data_info = (ExtList) data_info.get(0);
		}

		vr_env.append_css_def_td(VREnv.getClassID(this), this.decos);

		int i = 0;				// 20140526_masato
		int j = 0;				// 20140611_masato
		int k = 0;	
		if (decos.containsKey("vr_x")) {
			i = Integer.parseInt(decos.getStr("vr_x"));
			retFlag = true;
			if(!VRAttribute.componexflag){
				VRAttribute.compx[VRAttribute.cgcount] = i;
				VRAttribute.compflag[VRAttribute.cgcount] = 2;
			}
			VRAttribute.componexflag = true;
		}
		if (decos.containsKey("vr_y")) {///column->row_x, row->vr_y
			j = Integer.parseInt(decos.getStr("vr_y"));
			retFlag = true;
			if(!VRAttribute.componeyflag){
				VRAttribute.compy[VRAttribute.cgcount] = j;
				VRAttribute.compflag[VRAttribute.cgcount] = 2;
			}
			VRAttribute.componeyflag = true;
		}
		if (decos.containsKey("vr_z")) {
			k = Integer.parseInt(decos.getStr("vr_z"));
			retFlag = true;
			if(!VRAttribute.componezflag){
				VRAttribute.compz[VRAttribute.cgcount] = k;
				VRAttribute.compflag[VRAttribute.cgcount] = 2;
			}
			VRAttribute.componezflag = true;
		}

		if(vr_env.gLevel == 0){
			VRAttribute.floorarray.add(2);
		} else if(vr_env.gLevel == 1){
			VRAttribute.exharray.add(2);//G2の時はまだ使ってない
		}

		VRAttribute.gjudge++;

		while (this.hasMoreItems()) {
			VRAttribute.genre = "";

			// 20140528_masato
			VRAttribute.seq = 0;//n2 kotani

			try {
				int l=VRManager.gindex.get(vr_env.gLevel);
				VRManager.gindex.set(vr_env.gLevel,l+1);//gindex[]++
			} catch (Exception e) {
				VRManager.gindex.add(1);	//gindex[]=1
			}

			vr_env.gLevel++;
			Log.out("selectFlg" + VREnv.getSelectFlg());
			Log.out("selectRepeatFlg" + VREnv.getSelectRepeat());
			Log.out("formItemFlg" + VREnv.getFormItemFlg());

			String classid = VREnv.getClassID(tfe);

			//TODO: check this has nothing to do with VR
			if (GlobalEnv.isOpt() && !VREnv.getSelectRepeat()) {
				vr_env2.code.append("<tfe type=\"repeat\" dimension=\"2\"");
				vr_env2.code.append(" border=\"" + vr_env.tableBorder
						+ "\"");

				if (decos.containsKey("tablealign"))
					vr_env2.code.append(" align=\""
							+ decos.getStr("tablealign") + "\"");
				if (decos.containsKey("tablevalign"))
					vr_env2.code.append(" valign=\""
							+ decos.getStr("tablevalign") + "\"");

				if (decos.containsKey("class")) {
					vr_env2.code.append(" class=\"");
					vr_env2.code.append(decos.getStr("class") + " ");
				}
				if (vr_env.writtenClassId.contains(VREnv.getClassID(this))) {
					if (decos.containsKey("class")) {
						vr_env2.code.append(VREnv.getClassID(this) + "\"");
					} else {
						vr_env2.code.append(" class=\""
								+ VREnv.getClassID(this) + "\"");
					}
				} else if (decos.containsKey("class")) {
					vr_env2.code.append("\"");
				}

				if (decos.containsKey("tabletype")) {
					vr_env2.code.append(" tabletype=\""
							+ decos.getStr("tabletype") + "\"");
					if (decos.containsKey("cellspacing")) {
						vr_env2.code.append(" cellspacing=\""
								+ decos.getStr("cellspacing") + "\"");
					}
					if (decos.containsKey("cellpadding")) {
						vr_env2.code.append(" cellpadding=\""
								+ decos.getStr("cellpadding") + "\"");
					}
				}
				vr_env2.code.append(">");
			}

			this.worknextItem();

			//TODO: check what this does
			if (vr_env.notWrittenClassId.contains(classid)
					&& vr_env.code.indexOf(classid) >= 0) {
				vr_env.code.delete(vr_env.code.indexOf(classid),
						vr_env.code.indexOf(classid) + classid.length() + 1);
			}

			vr_env.gLevel--;
		}
		VRManager.gindex.set(vr_env.gLevel, 0);
		if(vr_env.gLevel == 0){
			VRManager.nest1count++;
		}

		for(int l=0; l<VRAttribute.elearray.size();l++){///n2 kotani
			vr_env.code.append("<n2 seq=\""+l+"\">\n" );
			vr_env.code.append(VRAttribute.elearray.get(l));
			vr_env.code.append("</n2>\n" );			
		}
		VRAttribute.elearray.clear();//初期化
		VRAttribute.seq = 0;//初期化

		if(VRAttribute.gjudge==1){
			VRAttribute.billnum++;
		}
		VRAttribute.gjudge--;

		if(vr_env.gLevel == 0){
			VRAttribute.componexflag = false;
			VRAttribute.componeyflag = false;
			VRAttribute.componezflag = false;
			VRAttribute.cgcount++;

			vr_env.code.append("</group>\n");
			VRAttribute.grouptag++;
			vr_env.code.append("<group>\n");
			VRAttribute.genrearray22.add(VRAttribute.genrecount);

		}				
		Log.out("TFEId = " + VREnv.getClassID(this));

		return null;
	}
}
