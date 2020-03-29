<?php
    $ret = array();
    $ret['result'] = "";
    
    $insert_col = "num_of_models,num_of_models,num_of_models,id,name,d";
    $notnullFlg = array(FALSE,FALSE,FALSE,TRUE,TRUE,TRUE);
    $checkboxFlg = array(FALSE,FALSE,TRUE,FALSE,FALSE,FALSE);
    $col_num = 6;
    $table = 'stock_model';

	$insert_str = "notnull";
	for($k=1; $k<=$col_num; $k++){
		$s = '';
		if(!$checkboxFlg[$k-1]){
			$s = $_POST['SSQL_insert1_words'.$k];
		}else{
			if(isset($_POST['SSQL_insert1_words'.$k]))
				foreach($_POST['SSQL_insert1_words'.$k] as $val)
        			$s .= $val.',';
			if(strstr($s, ','))	$s = substr($s, 0, -1);
		}
		$var[$k] = checkHTMLsc($s);
    	$var[$k] = str_replace(array("\r\n","\r","\n"), '<br>', $var[$k]);	//改行コードを<br>へ
    	if($notnullFlg[$k-1]){
    		if(trim($var[$k]) == "")	$insert_str = "";
    	}
    }

	$b = "";
	if($insert_str == ""){
        $b = '<font color="red">Please check the value.</font>';
	}else{
		//DBへ登録
        $insert_db1 = pg_connect ("host=localhost dbname=goto user=goto");
        $insert_sql = "INSERT INTO ".$table." (".$insert_col.") VALUES ($1,$2,$3,$4,$5,$6)";
        
        try{
			$result2 = pg_prepare($insert_db1, "ssql_insert_1", $insert_sql);
			$result2 = pg_execute($insert_db1, "ssql_insert_1", $var);
		 	$b = "Registration completed.";
		 	//$b = $insert_sql;
        }catch(Exception $e){
       		$b = '<font color=red>Insert failed.</font>';	//登録失敗
        }
        pg_close($insert_db1);
	}
	$ret['result'] = $b;
	//header("Content-Type: application/json; charset=utf-8");
	echo json_encode($ret);

function checkHTMLsc($str){
	return htmlspecialchars($str, ENT_QUOTES, 'UTF-8');
}
?>

