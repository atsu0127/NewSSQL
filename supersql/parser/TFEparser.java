package supersql.parser;

import java.util.Hashtable;

import supersql.codegenerator.*;
import supersql.common.GlobalEnv;
import supersql.common.Log;
import supersql.extendclass.ExtList;

public class TFEparser {

    TFE schemaTop;

    ExtList sch;

    Hashtable attp;

    private TFEtokenizer toks;

    private int Level = 0;

    private String dimch = "?,!%#";

    private int attno = 0;

    private CodeGenerator cg;

    private static String att_tmp;

    public TFEparser(String str, CodeGenerator cgenerator) {

        attp = new Hashtable();
        toks = new TFEtokenizer(str);
        cg = cgenerator;

        try {
            schemaTop = connector("");
            if (toks.hasMoreTokens()) {
                System.err.println("*** Token remained after parsing TFE ***");
                throw (new IllegalStateException());
            }
        } catch (IllegalStateException e) {
            System.err.println("Error[TFEparser]: Syntax Error in TFE");
            System.err.println(toks.DebugTrace());
            //tk////////////////////////////////////////////////////
            GlobalEnv.addErr("Error[TFEparser]: Syntax Error in TFE");
            //return ;
        	//System.exit(-1);
            //tk////////////////////////////////////////////////////
        }
        sch = schemaTop.makesch();

        Log.out("Schema is " + sch);

        Log.out("le0 is " + schemaTop.makele0());

    }

    private Grouper grouper() {

        String token;

        Level++;
        Log.out("===Grouper=== Level=" + Level);

        TFE operand1;

        /* next is one operand */
        /*
         * if (toks.lookToken().equals("[")) { toks.nextToken(); operand1 =
         * this.grouper(); } else { operand1 = connector(); }
         */
        operand1 = connector("]");

        token = toks.nextToken();

        int dim = dimch.indexOf(token);
        switch (dim) {
        case 0:
            Log.out("Grouping dim= ?");
            break;
        case 1:
            Log.out("Grouping dim= ,");
            break;
        case 2:
            Log.out("Grouping dim= !");
            break;
        case 3:
            Log.out("Grouping dim= %");
            break;
        case 4:
            Log.out("Grouping dim= #");
            break;
        default:
            System.err.println("*** Grouper Operator Not Found after ']' ***");
            throw (new IllegalStateException());
        }

        Grouper grp = cg.createGrouper(dim);
        grp.setTFE(operand1);

        this.setDecoration(grp);

        Log.out("===Grouper end=== Level=" + Level);
        Level--;

        return grp;

    }

    private TFE connector(String closeparen) {

        TFE outTFE;

        Log.out("---connector---");
        //        Log.out(toks.DebugTrace());

        /* read first operand */

        TFE operand = read_attribute();

        if (!toks.hasMoreTokens()) {
            Log.out("* not connected *");
            if (!closeparen.equals("")) {
                System.err.println("*** Can't close paren '" + closeparen
                        + "' ***");
                throw (new IllegalStateException());
            }
            outTFE = operand;
        } else if (toks.lookToken().equals("]")) {
            Log.out("* not connected *");
            if (!closeparen.equals("]")) {
                System.err.println("*** mismatch paren at ']' ***");
                throw (new IllegalStateException());
            }
            toks.nextToken();
            outTFE = operand;
        } else if (toks.lookToken().equals(")")) {
            Log.out("* not connected *");
            if (!closeparen.equals(")")) {
                System.err.println("*** mismatch paren at ')' ***");
                throw (new IllegalStateException());
            }
            toks.nextToken();
            outTFE = operand;
        } else if (toks.lookToken().equals("}")) {
            Log.out("* not connected *");
            if (!closeparen.equals("}")) {
                System.err.println("*** mismatch paren at '}' ***");
                throw (new IllegalStateException());
            }
            toks.nextToken();
            this.setDecoration(operand);
            outTFE = operand;
        } else {
            outTFE = connector_main(-10000, operand, closeparen);
        }

        Log.out("---close connector---");
        //        Log.out(toks.DebugTrace());

        return outTFE;

    }

    private Connector connector_main(int dim, TFE operand1, String closeparen) {

        Log.out("connector_main: dim=" + dim);
        String token = "";
        int dim2 = -10000;
        boolean different_op = false;
        ExtList tfes = new ExtList();
        tfes.add(operand1);

        while (true) {

            if (!toks.hasMoreTokens()) {
                if (!closeparen.equals("")) {
                    toks.nextToken();
                    System.err.println("*** Detect end of TFE before paren '"
                            + closeparen + "' ***");
                    throw (new IllegalStateException());
                }
                break;
            }

            /* read next oprator */
            token = toks.nextToken();
            Log.out("connector_operator*token=" + token);

            if (token.equals("[")) {
                /* error for operand */
                System.err.println("*** Found '[' after Operand ***");
                throw (new IllegalStateException());

            } else if (token.equals("]")) {
                if (!closeparen.equals("]")) {
                    System.err.println("*** mismatch paren at ']' ***");
                    throw (new IllegalStateException());
                }
                /* close group */
                break;

            } else if (token.equals("}")) {
                if (!closeparen.equals("}")) {
                    System.err.println("*** mismatch paren at '}' ***");
                    throw (new IllegalStateException());
                }
                /* close paren */
                break;

            } else if (token.equals(")")) {
                if (!closeparen.equals(")")) {
                    System.err.println("*** mismatch paren at ')' ***");
                    throw (new IllegalStateException());
                }
                /* close paren (func) */
                break;

            } else if (dimch.indexOf(token) == -1) {

                /* error for operand */
                System.err.println("*** Found Illegal Token after Operand ***");
                throw (new IllegalStateException());

            }

            dim2 = dimch.indexOf(token);
            Log.out("nextch : " + toks.lookToken());
            if (token.equals(toks.lookToken())) {
                dim2 = -dim2;
                toks.nextToken();
                Log.out("duplicated");
            }

            if (dim == -10000) {
                /* first operator */
                dim = dim2;
                Log.out("operator : " + token);
            } else if (dim != dim2) {

                /* different oprator */
                Log.out("different op dim=" + dim + " dim2=" + dim2);
                different_op = true;
                break;

            }

            /* read next operand */

            tfes.add(read_attribute());

        }

        Log.out("connector closed dim:" + dim);
        //        Log.out(toks.DebugTrace());

        Connector con = cg.createConnector(dim);

        for (int i = 0; i < tfes.size(); i++) {
            con.setTFE((TFE) (tfes.get(i)));
        }

        if (token.equals("}"))
            this.setDecoration(con);

        if (different_op) {
            Log.out("--- different connector start---");
            toks.prevToken();
            con = connector_main(-10000, con, closeparen);
            Log.out("--- different connector end---");
        }

        return con;

    }

    private TFE read_attribute() {

        String token;

        TFE out_tfe = null;

        token = toks.nextToken();
        Log.out("attribute*token=" + token);

        if (token.equals("[")) {

            /* grouping operand */
            out_tfe = grouper();

        } else if (token.equals("]")) {

            /* error for first ']' */
            System.err.println("*** Found ']' after Operator or Paren ***");
            throw (new IllegalStateException());

        } else if (token.equals("{")) {

            /* open paren */
            out_tfe = connector("}");

        } else if (dimch.indexOf(token) > -1) {

            /* error for operator */
            System.err
                    .println("*** Found Operator after Operator or Paren ***");
            throw (new IllegalStateException());

        } else if (toks.lookToken().equals("(")) {
            /* function name */
            out_tfe = func_read(token);

        } else {

            Log.out("attribute=" + token);

            Attribute att = makeAttribute(token);

            out_tfe = att;

        }

        return out_tfe;

    }

    private Attribute makeAttribute(String token) {

        String line;
        String name;
        String key = null;

        int equalidx = token.indexOf('=');

        if (equalidx != -1) {
            // found key = att
            key = token.substring(0, equalidx);
            token = token.substring(equalidx + 1);

            //tk to ignore space between = and value/////////////////
            key = key.trim();
            //tk///////////////////

            Log.out("[makeAttiribute] === Attribute Key : " + key + " ===");
        } else {
        }

        int as_string = token.toLowerCase().indexOf(" as ");
        if (as_string != -1) {
            line = (String) (token.substring(0, as_string));
            name = (String) (token.substring(as_string + 4));
        } else {
            line = token;
            name = token;
        }

        //tk to ignore space between = and value/////////////////
        line = line.trim();
        name = name.trim();
        att_tmp = name;
        //tk//////////////////////////////////
        Log.out("[makeAttribute] line : " + line);
        Log.out("[makeAttribute] name : " + name);

        Attribute att = cg.createAttribute();
        attno = att.setItem(attno, name, line, key, attp);

        this.setDecoration(att);

        return att;

    }

    private Function func_read(String fn) {

        String token;
        Function fnc = cg.createFunction();
        fnc.setFname(fn);
        FunctionData fnd = new FunctionData(fn);

        String name, value;

        if (!toks.nextToken().equals("(")) {
            System.err
                    .println("*** token '(' not Found after Function Name token ***");
            throw (new IllegalStateException());
        } // not start "("

        Log.out("[func*read start funcname]=" + fn);
        /* func_read */
        TFE read_tfe = connector(")");

        Log.out("[func*TFE]=" + read_tfe.makele0());

        if (read_tfe instanceof Connector) {
            // many Attribute
            for (int i = 0; i < ((Connector) read_tfe).tfeitems; i++) {
            	fnc.setArg(makeFuncArg(((Connector) read_tfe).gettfe(i)));
            	if(fn.equals("select") && i ==0){
            		fnc.addDeco("select",att_tmp);
            	}
            }
        } else {
            // only one Attribute
            fnc.setArg(makeFuncArg(read_tfe));
        	if(fn.equals("select")){
        		fnc.addDeco("select",att_tmp);
        	}
        }

        this.setDecoration(fnc);

        return fnc;

    }

    private FuncArg makeFuncArg(TFE arg) {
        FuncArg out_fa;
        Log.out("argsaregs: "+arg);

        if (arg instanceof Attribute) {
            out_fa = new FuncArg(((Attribute) arg).getKey(), 1, arg);
        } else {
            out_fa = new FuncArg("TFE", 1, arg);
        }


        return out_fa;
    }

    private void setDecoration(TFE tfe) {

        String token;

        // @で始まってい?か
        if (!toks.lookToken().equals("@"))
            return;
        toks.nextToken();

        // その次が {
        if (!toks.nextToken().equals("{")) {
            System.err
                    .println("*** Illegal Token Found after Decoration token '@' ***");
            throw (new IllegalStateException());
        }

        //hanki start
        if (fromPreprocessorOnly(tfe)) {
        	return;
        }
        //hanki end

        Log.out("@ decoration found @");
        //        Log.out(toks.DebugTrace());

        String name, value;
        int equalidx;

        while (toks.hasMoreTokens()) {

            name = new String();
            value = new String();

            // read name
            token = toks.nextToken();


            Log.out("decoration*token=" + token);

            if (token.equals("}")) {
                break;
            }

            equalidx = token.indexOf('=');
            if (equalidx != -1) {
                // key = idx
                name = token.substring(0, equalidx);
                value = token.substring(equalidx + 1);
                while (toks.hasMoreTokens()) {
                    token = toks.lookToken();
                    Log.out("decoration*looktoken=" + token);
                    if (token.equals(",") || token.equals("}")) {
                        break;
                    }
                    value = value.concat(toks.nextToken());
                }
                decoration_out(tfe, name, value);
            } else {
                // key only
                decoration_out(tfe, token, "");
            }

            token = toks.nextToken();
            Log.out("decoration*token=" + token);
            if (token.equals("}")) {
                // end of decoration
                break;
            }
            if (!token.equals(",")) {
                // not close in "}"
                System.err
                        .println("*** Found Illegal Token after Decoration value ***");
                throw (new IllegalStateException());
            }
        }
        Log.out("@ decoration end @");
        //        Log.out(toks.DebugTrace());

    }

   //hanki start
    private boolean fromPreprocessorOnly(TFE tfe) {

    	String token;

    	do {

    		/* "order by" found */
            if (toks.lookToken().toLowerCase().indexOf("asc") > -1 ||
            	toks.lookToken().toLowerCase().indexOf("desc") > -1) {

            	Log.out("@ order by found @");
            	token = toks.nextToken();

            	tfe.setOrderBy(token);

            	/* decoration doesn't exist originally */
            	if (toks.lookToken().equals("}")) {
            		toks.nextToken();	// skip "}"
            		return true;
            	/* decoration exists originally */
            	} else if (toks.lookToken().equals(",")) {
            		toks.nextToken();	// skip ","
            	}

            /* "aggregate functions" found */
            } else if (toks.lookToken().equalsIgnoreCase("max") ||
    				   toks.lookToken().equalsIgnoreCase("min") ||
    				   toks.lookToken().equalsIgnoreCase("avg") ||
    				   toks.lookToken().equalsIgnoreCase("sum") ||
            		   toks.lookToken().equalsIgnoreCase("count") /*||
            		   //added by goto 20130122
            		   toks.lookToken().equalsIgnoreCase("slideshow")*/) {


            	Log.out("@ aggregate functions found @");
            	token = toks.nextToken();

            	tfe.setAggregate(token);

            	/* decoration doesn't exist originally */
            	if (toks.lookToken().equals("}")) {
            		toks.nextToken();	// skip "}"
            		return true;
            	/* decoration exists originally */
            	} else if (toks.lookToken().equals(",")) {
            		toks.nextToken();	// skip ","
               	}

            /* neither "order by" nor "aggregate functions" found */
            } else {
            	return false;
            }

    	} while (toks.lookToken().toLowerCase().indexOf("asc") > -1 ||
    			 toks.lookToken().toLowerCase().indexOf("desc") > -1 ||
    			 toks.lookToken().equalsIgnoreCase("max") ||
				 toks.lookToken().equalsIgnoreCase("min") ||
				 toks.lookToken().equalsIgnoreCase("avg") ||
				 toks.lookToken().equalsIgnoreCase("sum") ||
				 toks.lookToken().equalsIgnoreCase("count") /*||
				 //added by goto 20130122
				 toks.lookToken().equalsIgnoreCase("slideshow")*/);
    	

    	return false;

    }
    //hanki end
    /*
     * // 装?指定＠以下を解析 // private Function deco_read(String fn) { private void
     * deco_read(TFE tfe) { // 最初が@で始まっていなけ?ば装?なし if
     * (!toks.lookToken().equals("@")) return; toks.nextToken();
     *
     * Log.out("@@ start read decoration @@");
     *
     * String token; Function fnc = cg.createFunction(); fnc.setFname(fn);
     * FunctionData fnd = new FunctionData(fn);
     *
     * if (!toks.nextToken().equals("{")) { Exception e = new Exception(); } //
     * not start "{" // deco_read TFE read_tfe = connector();
     * Log.out("[decoration]=" + read_tfe.makele0());
     *
     * if (read_tfe instanceof Connector) { // many Attribute for (int i = 0; i <
     * ((Connector) read_tfe).tfeitems; i++) {
     * fnc.setArg(makeFuncArg(((Connector) read_tfe).gettfe(i))); } } else { //
     * only one Attribute fnc.setArg(makeFuncArg(read_tfe)); }
     *
     * this.setDecoration(fnc); return; // return fnc; }
     */

    private void decoration_out(TFE tfe, String name, Object value) {

        /* 暫?的にStringしか読めない */
        tfe.addDeco(name, (String) value);
        Log.out("[decoration name=" + name + " value=" + value + "]");

    }

    public TFE get_TFEschema() {

        return schemaTop;

    }

    public void debugout() {

        Log.out("========================================");
        Log.out("  output Schema Tree");
        Log.out("========================================");
        schemaTop.debugout(0);
    }

    public Hashtable get_attp() {
        return this.attp;
    }

}