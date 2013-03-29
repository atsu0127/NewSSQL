package supersql.codegenerator;

import java.util.Hashtable;
import java.util.Enumeration;

import supersql.common.Log;

public class DecorateList extends Hashtable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	Connector connector;	//add oka

	public String getStr(String s) {
		Object o = this.get(s);
		if (o instanceof String){
			String ret = (String) (this.get(s));
			if(ret.startsWith("\"") && ret.endsWith("\"")){
				ret = ret.substring(1,ret.length()-1);
			}
			return ret;
		}
		return null;
	}

	public TFE getTFE(String s) {
		Object o = this.get(s);
		if (o instanceof TFE)
			return (TFE) (this.get(s));
		return null;
	}

	public void debugout(int count) {
		if (this.size() > 0) {

			Debug dbgout = new Debug();
			dbgout.prt(count, "<DecorateList>");
			Enumeration e = this.keys();
			while (e.hasMoreElements()) {
				String key = (String) (e.nextElement());
				Object val = this.get(key);
				if (val == null) {
					dbgout.prt(count + 1, "<Deco Key=" + key + "/>");
				} else	if (val instanceof String) {
					//start oka
					if(key.equals("update")){
						Log.out("@ update found @");
						Connector.update_flag = true;
					}else if(key.equals("insert")){
						Log.out("@ insert found @");
						Connector.insert_flag = true;
					}else if(key.equals("delete")){
						Log.out("@ delete found @");
						Connector.delete_flag = true;
					}else if(key.equals("login")){
						Log.out("@ login found @");
						Connector.login_flag = true;
					}else if(key.equals("logout")){
						Log.out("@ logout found @");
						Connector.logout_flag = true;
					}
					//end oka
					
					dbgout.prt(count + 1, "<Deco Key=" + key
								+ " type=value value=" + val + "/>");
						
				} else if (val instanceof TFE) {
					dbgout.prt(count + 1, "<Deco Key=" + key + " type=TFE>");
					((TFE) val).debugout(count + 2);
					dbgout.prt(count + 1, "</Deco>");
				} else {
					dbgout.prt(count + 1, "<Deco Key=" + key + ">");
					dbgout.prt(count + 2, val.toString());
					dbgout.prt(count + 1, "</Deco>");
				}
			}
			
			dbgout.prt(count, "</DecorateList>");
		}
	}

}