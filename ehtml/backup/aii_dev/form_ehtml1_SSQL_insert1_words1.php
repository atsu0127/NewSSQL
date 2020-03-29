<?php
	$checked_num = 1;
	$name = 'SSQL_insert1_words1';
	$id = 'SSQL_insert1_words1';
   
   $sql = 'select distinct num_of_models from stock_model';
	try{
		$db = pg_connect("host=localhost dbname=goto user=goto");
		$results = pg_query($db, $sql);
		$i = 1;
		while ($row = pg_fetch_row($results)) {
			$val = $row[0];
			$insert_val = $row[1];
			if(is_null($insert_val))	$insert_val = $val;
			echo '			<option value="'.$insert_val.'">'.$val.'</option>
';
	    	$i++;
		}
	}catch(Exception $e){
		echo '<font color=red>Select failed.<br>'.$e.'</font>';
	}
	pg_close($db);
?>

