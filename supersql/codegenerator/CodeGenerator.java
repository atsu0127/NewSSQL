package supersql.codegenerator;

import supersql.codegenerator.HTML.*;
import supersql.codegenerator.HTML5.*;
import supersql.codegenerator.TESTHTML.*;
import supersql.codegenerator.Mobile_HTML5.Mobile_HTML5Factory;
import supersql.codegenerator.PDF.*;
import supersql.codegenerator.SWF.SWFFactory;
import supersql.codegenerator.X3D.*;
import supersql.codegenerator.XML.XMLFactory;
import supersql.common.GlobalEnv;
import supersql.common.Log;
import supersql.extendclass.ExtList;
import supersql.parser.SSQLparser;

//import codegenerator.XML.*;

/**
 * コードジェネ?ータマネージャクラス 指定さ?た媒体によってコードジェネ?ータの構成部品を生成す? 工?(Factory)を生成す?
 */
public class CodeGenerator{
	Factory factory;

	public int TFEid;

	public Manager manager;
	/**
	 * コンストラクタ
	 */
	public CodeGenerator() {
	}

	public CodeGenerator(int id){
		TFEid = id;
	}
	/**
	 * 初?化を行う
	 */
	public void initiate() {
		if (factory != null) {
			Log.out("factory is " + factory);
			factory.createLocalEnv();
			manager = factory.createManager();
		}
	}

	/**
	 * 指定さ?た媒体によって笹卮を委譲す?ための工?(Factory)? 生成して配置す?
	 */
	public void setFactory(String media) {
		if (media.toLowerCase().equals("pdf")) {
			factory = new PDFFactory();
		} else if (media.toLowerCase().equals("html")) {
			factory = new HTMLFactory();
		} else if (media.toLowerCase().equals("x3d")) {
			factory = new X3DFactory();
		} else if(media.toLowerCase().equals("xml")){
			factory = new XMLFactory();
		} else if (media.toLowerCase().equals("swf")) {
		 	factory = new SWFFactory();
		} else if (media.toLowerCase().equals("html5")) {
		 	factory = new HTML5Factory();
		} else if (media.toLowerCase().equals("mobile_html5")) {	//added by goto 20121217
		 	factory = new Mobile_HTML5Factory();
		}  else if (media.toLowerCase().equals("csv")) {
			factory = new SWFFactory();
		} else if (media.toLowerCase().equals("testhtml")) {
			factory = new TESTHTMLFactory();
		}
		/*
		 * else if(media.toLowerCase().equals("xml")){ factory = new
		 * XMLFactory(); }
		 */
		else {
			System.err.println("Error[Media]: valid medium not found");
			GlobalEnv.addErr("Error[Media]: valid medium not found");
			//comment out  by chie
			//System.exit(-1);
		}
	}

	/**
	 * 演算子Connectorの下位クラスインスタンスを生成して返す
	 */
	public Connector createConnector(int dim) {
		Connector connector = null;
		if (dim == 0)
			connector = factory.createC0(manager);
		else if (dim == 1)
			connector = factory.createC1(manager);
		else if (dim == 2)
			connector = factory.createC2(manager);
		else if (dim == 3 || dim == -3)
			connector = factory.createC3(manager);
		else if (dim == 4)
			connector = factory.createC4(manager);
		else {
		    /* undefine Operator */
		    System.err.println("*** Illegal Operator for Connector ***");
		    throw (new IllegalStateException());

		}
		connector.setId(TFEid++);
		return connector;
	}

	/**
	 * 演算子Grouperの下位クラスインスタンスを生成して返す
	 */
	public Grouper createGrouper(int dim) {
		Grouper grouper = null;
		if (dim == 0)
			grouper = factory.createG0(manager);
		else if (dim == 1)
			grouper = factory.createG1(manager);
		else if (dim == 2)
			grouper = factory.createG2(manager);
		else if (dim == 3)
			grouper = factory.createG3(manager);
		else if (dim == 4)
			grouper = factory.createG4(manager);
		else {
		    /* undefine Operator */
		    System.err.println("*** Illegal Operator for Grouper ***");
		    throw (new IllegalStateException());
		}
		grouper.setId(TFEid++);
		return grouper;
	}

	/**
	 * Attributeの下位クラスインスタンスを生成して返す
	 */
	public Attribute createAttribute() {
	    Attribute attribute = factory.createAttribute(manager);
		attribute.setId(TFEid++);
		return attribute;
	}

	/**
	 * Functionの下位クラスインスタンスを生成して返す
	 */
	public Function createFunction() {
	    Function function = factory.createFunction(manager);
		function.setId(TFEid++);
		return function;
	}

	/**
	 * 構造情報、装?情報、RelationクラスのオブジェクトからO2C, SQLソース を生成す?
	 */

	public void generateCode(SSQLparser parser, ExtList data_info) {
		TFE tfe_info = parser.get_TFEschema();

		//	必要ならコメントアウト外し、Managerの中も変更
		//	manager.preProcess(tab,le,le1,le2,le3);
		//	manager.createSchema(tab,le,le1,le2,le3);

		Log.out("===============================");
		Log.out("     generateCode is start     ");
		Log.out("===============================");

		// ?番外が Grouperのときにdata_infoを調整す?
		if (tfe_info instanceof Grouper && data_info.size() != 0) {
			data_info = (ExtList) data_info.get(0);
		}

		//tk start//////////////////////////////////////////////
//		if(data_info.size() == 0)
//			manager.generateCodeNotuple(tfe_info);
//		else
			manager.generateCode(tfe_info, data_info);
		//tk end///////////////////////////////////////////////
		manager.finish();


	};

	//tk start//////////////////////////////////////////////////////////////////////////////
	public StringBuffer generateCode2(SSQLparser parser, ExtList data_info) {
		TFE tfe_info = parser.get_TFEschema();

		//	必要ならコメントアウト外し、Managerの中も変更
		//	manager.preProcess(tab,le,le1,le2,le3);
		//	manager.createSchema(tab,le,le1,le2,le3);

		Log.out("===============================");
		Log.out("     generateCode2 is start     ");
		Log.out("===============================");


		// ?番外が Grouperのときにdata_infoを調整す?
		if (tfe_info instanceof Grouper && data_info.size() != 0) {
			data_info = (ExtList) data_info.get(0);
		}
		Log.out("data_info.size " + data_info.size());

		if(data_info.size() == 0)
			return manager.generateCodeNotuple(tfe_info);
		else
			return manager.generateCode2(tfe_info, data_info);

	};
	public StringBuffer generateCode3(SSQLparser parser, ExtList data_info) {
		TFE tfe_info = parser.get_TFEschema();

		//	必要ならコメントアウト外し、Managerの中も変更
		//	manager.preProcess(tab,le,le1,le2,le3);
		//	manager.createSchema(tab,le,le1,le2,le3);

		Log.out("===============================");
		Log.out("     generateCode3 is start     ");
		Log.out("===============================");

		// ?番外が Grouperのときにdata_infoを調整す?
		if (tfe_info instanceof Grouper && data_info.size() != 0) {
			data_info = (ExtList) data_info.get(0);
		}
		Log.out("data_info.size " + data_info.size());

		if(data_info.size() == 0)
			return manager.generateCodeNotuple(tfe_info);
		else
			return manager.generateCode3(tfe_info, data_info);

	};
	public StringBuffer generateCode4(SSQLparser parser, ExtList data_info) {
		TFE tfe_info = parser.get_TFEschema();

		//	必要ならコメントアウト外し、Managerの中も変更
		//	manager.preProcess(tab,le,le1,le2,le3);
		//	manager.createSchema(tab,le,le1,le2,le3);

		Log.out("===============================");
		Log.out("     generateCode4 is start     ");
		Log.out("===============================");

		// ?番外が Grouperのときにdata_infoを調整す?
		if (tfe_info instanceof Grouper && data_info.size() != 0) {
			data_info = (ExtList) data_info.get(0);
		}
		Log.out("data_info.size " + data_info.size());

		if(data_info.size() == 0)
			return manager.generateCodeNotuple(tfe_info);
		else
			return manager.generateCode4(tfe_info, data_info);

	};
	public StringBuffer generateCssfile(SSQLparser parser, ExtList data_info) {
		TFE tfe_info = parser.get_TFEschema();

		//	必要ならコメントアウト外し、Managerの中も変更
		//	manager.preProcess(tab,le,le1,le2,le3);
		//	manager.createSchema(tab,le,le1,le2,le3);

		Log.out("==================================");
		Log.out("     generateCssfile is start     ");
		Log.out("==================================");

		// ?番外が Grouperのときにdata_infoを調整す?
		if (tfe_info instanceof Grouper && data_info.size() != 0) {
			data_info = (ExtList) data_info.get(0);
		}
		Log.out("data_info.size " + data_info.size());

		if(data_info.size() == 0)
			return manager.generateCodeNotuple(tfe_info);
		else
			return manager.generateCssfile(tfe_info, data_info);

	};

	//tk end///////////////////////////////////////////////////////////////////////////////
}