package supersql.codegenerator.HTML5;

import java.io.*;
import java.util.Vector;

import supersql.codegenerator.*;
import supersql.common.GlobalEnv;
import supersql.common.Log;
import supersql.dataconstructor.DataConstructor;
import supersql.extendclass.ExtList;

public class HTML5Manager extends Manager{

    //����
    HTML5Env html5_env;
    HTML5Env html5_env2;

    //���󥹥ȥ饯��
    public HTML5Manager(HTML5Env henv,HTML5Env henv2) {
        this.html5_env = henv;
        this.html5_env2 = henv2;
    }


    @Override
	public void generateCode(TFE tfe_info, ExtList data_info) {

        HTML5Env.initAllFormFlg();

        html5_env.countfile = 0;
        html5_env.code = new StringBuffer();
        html5_env.css = new StringBuffer();
        html5_env.header = new StringBuffer();
        html5_env.footer = new StringBuffer();
        html5_env.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env.written_classid = new Vector();
        html5_env.not_written_classid = new Vector();
        html5_env2.countfile = 0;
        html5_env2.code = new StringBuffer();
        html5_env2.css = new StringBuffer();
        html5_env2.header = new StringBuffer();
        html5_env2.footer = new StringBuffer();
        html5_env2.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env2.written_classid = new Vector<String>();
        HTML5Env localenv = new HTML5Env();

        /*** start oka ***/


        // ���Ϥ�?�ե���?̾����?
        getOutfilename();

        Log.out("[HTMLManager:generateCode]");

        // ?�ֳ�¦��G3��??
        if (tfe_info instanceof HTML5G3) {
            tfe_info.work(data_info);
            return;
        }

        // ?�ֳ�¦��G3�Ǥʤ�??]
        html5_env.filename = html5_env.outfile + ".html";
        html5_env2.filename = html5_env.outfile + ".xml";

        html5_env.setOutlineMode();

        if(data_info.size() == 0
            //added by goto 20130306  "FROMなしクエリ対策 3/3"
           	&& !DataConstructor.SQL_string.equals("SELECT DISTINCT  FROM ;"))
        {
        	Log.out("no data");
        	html5_env.code.append("<div class=\"nodata\" >");
        	html5_env.code.append("NO DATA FOUND");
        	html5_env.code.append("</div>");
        }
        else
        	tfe_info.work(data_info);

        html5_env.getHeader();
        html5_env.getFooter();
        html5_env2.header.append("<?xml version=\"1.0\" encoding=\""+html5_env.getEncode()+"\"?><SSQL>");
        html5_env2.footer.append("</SSQL>");
        try {
        	if(!GlobalEnv.isOpt()){
	        	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
	                    html5_env.filename)));
	            pw.println(html5_env.header);
	            pw.println(html5_env.code);
	            pw.println(html5_env.footer);
	            pw.close();
        	}
            //xml
	        if(GlobalEnv.isOpt()){

            	/*
            	int i=0;
	            while(html_env2.code.indexOf("&",i) != -1){
	            	i = html_env2.code.indexOf("&",i);
	            	html_env2.code = html_env2.code.replace(i,i+1, "&amp;");
	            	i++;
	            }
	            */

	            html5_env2.filename = html5_env.outfile + ".xml";
	            PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(
	                    html5_env2.filename)));
	            pw2.println(html5_env2.header);
	            pw2.println(html5_env2.code);
	            pw2.println(html5_env2.footer);
	            pw2.close();
	            HTML5optimizer xml = new HTML5optimizer();
	            String xml_str =  xml.generateHtml(html5_env2.filename);
	        	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
	                    html5_env.filename)));
				pw.println(html5_env.header);
				pw.println(xml_str);
				//StringBuffer footer = new StringBuffer("</div></body></html>");
				pw.println(html5_env.footer);
				pw.close();
            }
            HTML5Env.initAllFormFlg();
        } catch (FileNotFoundException fe) {
        	fe.printStackTrace();
        	System.err.println("Error: specified outdirectory \""
                    + html5_env.outdir + "\" is not found to write " + html5_env.filename );
        	GlobalEnv.addErr("Error: specified outdirectory \""
                    + html5_env.outdir + "\" is not found to write " + html5_env.filename );
        	//comment out by chie
        	//System.exit(-1);
        } catch (IOException e) {
            System.err.println("Error[HTMLManager]: File IO Error in HTMLManager");
            e.printStackTrace();
           	GlobalEnv.addErr("Error[HTMLManager]: File IO Error in HTMLManager");
            //comment out by chie
        	//System.exit(-1);
        }

    }

    //tk start///////////////////////////////////////////////////////////////////////
    @Override
	public StringBuffer generateCode2(TFE tfe_info, ExtList data_info) {
    	HTML5Env.initAllFormFlg();

        html5_env.countfile = 0;
        html5_env.code = new StringBuffer();
        html5_env.css = new StringBuffer();
        html5_env.header = new StringBuffer();
        html5_env.footer = new StringBuffer();
        html5_env.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env.written_classid = new Vector();
        html5_env.embedflag = true;


        html5_env2.countfile = 0;
        html5_env2.code = new StringBuffer();
        html5_env2.css = new StringBuffer();
        html5_env2.header = new StringBuffer();
        html5_env2.footer = new StringBuffer();
        String xml_str = null;
        StringBuffer returncode = new StringBuffer();
        // ���Ϥ�?�ե���?̾����?
        getOutfilename();

        Log.out("[HTML5Manager:generateCode2]");

        // ?�ֳ�¦��G3��??
        if (tfe_info instanceof HTML5G3) {
            tfe_info.work(data_info);
            return html5_env.code;
        }
        // ?�ֳ�¦��G3�Ǥʤ�??
        html5_env.setOutlineMode();
        tfe_info.work(data_info);

        html5_env2.header.append("<?xml version=\"1.0\" encoding=\"Shift_JIS\"?><SSQL>");
        html5_env2.footer.append("</SSQL>");


        if(GlobalEnv.isOpt()){
        	int i=0;
            while(html5_env2.code.indexOf("&",i) != -1){
            	i = html5_env2.code.indexOf("&",i);
            	html5_env2.code = html5_env2.code.replace(i,i+1, "&amp;");
            	i++;
            }
        	StringBuffer xml_string = new StringBuffer();
        	xml_string.append(html5_env2.header);
        	xml_string.append(html5_env2.code);
        	xml_string.append(html5_env2.footer);
        	HTML5optimizer xml = new HTML5optimizer();
        	System.out.println(xml_string);
        	xml_str = xml.generateHtml(xml_string);
        	returncode.append(xml_str);
        }
        html5_env.embedflag = false;

        if(html5_env.script.length() >= 5)
        {
        	StringBuffer result = new StringBuffer();

        	result.append(html5_env.script);
        	result.append("<end of script>\n");
        	result.append(html5_env.code);

        	return result;
        }
        else
        {
	        if(GlobalEnv.isOpt())
	        	return returncode;
	        else
	        	return html5_env.code;

        }
    }
    @Override
	public StringBuffer generateCodeNotuple(TFE tfe_info) {
    		Log.out("no data found");
    	html5_env.code = new StringBuffer();
    	html5_env.code.append("<div class=\"nodata\" >");
    	html5_env.code.append("NO DATA FOUND");
    	html5_env.code.append("</div>");

    	return html5_env.code;
    }

    @Override
	public StringBuffer generateCode3(TFE tfe_info, ExtList data_info) {
    	HTML5Env.initAllFormFlg();

        html5_env.countfile = 0;
        html5_env.code = new StringBuffer();
        html5_env.css = new StringBuffer();
        html5_env.header = new StringBuffer();
        html5_env.footer = new StringBuffer();
        html5_env.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env.written_classid = new Vector();
        html5_env.embedflag = true;
        // ���Ϥ�?�ե���?̾����?
        getOutfilename();

        Log.out("[HTML5Manager:generateCode]");

        // ?�ֳ�¦��G3��??
        if (tfe_info instanceof HTML5G3) {
            tfe_info.work(data_info);
            return html5_env.code;
        }
        // ?�ֳ�¦��G3�Ǥʤ�??


        html5_env.setOutlineMode();
        tfe_info.work(data_info);
//        html_env.getCSS();
        html5_env.embedflag = false;
        Log.out("header : "+ html5_env.header);
        return html5_env.css;
    }

    @Override
	public StringBuffer generateCode4(TFE tfe_info, ExtList data_info) {
    	HTML5Env.initAllFormFlg();
        html5_env.countfile = 0;
        html5_env.code = new StringBuffer();
        html5_env.css = new StringBuffer();
        html5_env.header = new StringBuffer();
        html5_env.footer = new StringBuffer();
        html5_env.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env.written_classid = new Vector();

        html5_env2.countfile = 0;
        html5_env2.code = new StringBuffer();
        html5_env2.css = new StringBuffer();
        html5_env2.header = new StringBuffer();
        html5_env2.footer = new StringBuffer();
        html5_env2.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env2.written_classid = new Vector<String>();

        HTML5Env localenv = new HTML5Env();

        // ���Ϥ�?�ե���?̾����?
        getOutfilename();

        Log.out("[HTML5Manager:generateCode]");


        // ?�ֳ�¦��G3�Ǥʤ�??
        html5_env.filename = html5_env.outfile + ".html";
        html5_env2.filename = html5_env.outfile + ".xml";

        html5_env.setOutlineMode();
        tfe_info.work(data_info);

        html5_env.getHeader();
        html5_env.getFooter();
        html5_env.embedflag = false;
        Log.out("header : "+ html5_env.header);

        StringBuffer headfoot = new StringBuffer(html5_env.header + " ###split### " + html5_env.footer);

        return headfoot;
    }
  @Override
public StringBuffer generateCssfile(TFE tfe_info, ExtList data_info) {

        html5_env.countfile = 0;
        html5_env.code = new StringBuffer();
        html5_env.css = new StringBuffer();
        html5_env.header = new StringBuffer();
        html5_env.footer = new StringBuffer();
        html5_env.foreach_flag = GlobalEnv.getForeachFlag();
        html5_env.written_classid = new Vector();
        html5_env.embedflag = true;
        // ���Ϥ�?�ե���?̾����?
        getOutfilename();

        Log.out("[HTML5Manager:generateCode]");

        html5_env.setOutlineMode();
        tfe_info.work(data_info);
        html5_env.embedflag = false;
        Log.out("header : "+ html5_env.header);
        return html5_env.cssfile;
    }
    //tk end///////////////////////////////////////////////////////////////////////////////

    private void getOutfilename() {
        String file = GlobalEnv.getfilename();
        String outdir = GlobalEnv.getoutdirectory();
        String outfile = GlobalEnv.getoutfilename();
        html5_env.outdir = outdir;

        /*
         * ���ϥե���?(outfilename)�����ꤵ?�Ƥ�???
         * html5_env.outfile��globalenv.outfilename�ˤ�?
         * ��?�ʳ��ΤȤ��ϥ���?�ե���?��̾��(filename)�ˤ�?
         */
        if (outfile == null) {
        	if (file.toLowerCase().indexOf(".sql")>0) {
        		html5_env.outfile = file.substring(0, file.toLowerCase().indexOf(".sql"));
        	} else if (file.toLowerCase().indexOf(".ssql")>0) {
        		html5_env.outfile = file.substring(0, file.toLowerCase().indexOf(".ssql"));
        	}
        } else {
            html5_env.outfile = getOutfile(outfile);
        }

        if (html5_env.outfile.indexOf("/") > 0) {
            html5_env.linkoutfile = html5_env.outfile.substring(html5_env.outfile
                    .lastIndexOf("/") + 1);
        } else {
            html5_env.linkoutfile = html5_env.outfile;
        }
/*
        //tk start
        if(html5_env.outfile.lastIndexOf("\\") != -1)
        {
        	html5_env.outfile = html5_env.outfile.substring(html5_env.outfile.lastIndexOf("\\"));
        	Log.out("outfile log:"+html5_env.outfile);
        }
        //tk end
  */
        /*
         * ������ǥ�?����?(outdirectory)�����ꤵ?�Ƥ�???
         * outdirectory��filename��Ĥʤ�����Τ�file�Ȥ�?
         */
        if (outdir != null) {
            connectOutdir(outdir, outfile);
        }
    }

    private String getOutfile(String outfile) {
        String out = new String();
        if (outfile.indexOf(".html") > 0) {
            out = outfile.substring(0, outfile.indexOf(".html"));
        } else {
            out = outfile;
        }
        return out;
    }

    private void connectOutdir(String outdir, String outfile) {
        String tmpqueryfile = new String();
        if (html5_env.outfile.indexOf("/") > 0) {
            if (outfile != null) {
                if (html5_env.outfile.startsWith(".")
                        || html5_env.outfile.startsWith("/")) {
                    tmpqueryfile = html5_env.outfile.substring(html5_env.outfile
                            .indexOf("/") + 1);
                }
            } else {
                tmpqueryfile = html5_env.outfile.substring(html5_env.outfile
                        .lastIndexOf("/") + 1);
            }
        } else {
            tmpqueryfile = html5_env.outfile;
        }
        if (!outdir.endsWith("/")) {
            outdir = outdir.concat("/");
        }
        html5_env.outfile = outdir.concat(tmpqueryfile);
    }

    @Override
	public void finish() {

    }
}