<?php
    $ret = array();
    $ret['result'] = "";

    $table = 'stock_model s';
    $where = "";
    $stream_a_Flg = array("false");
    $stream_mail_Flg = array("false");
    $stream_pop_Flg = array("false");
    $groupby = "";
    $having = "";
    $orderby = " ORDER BY s.num_of_models DESC ";
    $orderby_atts = ", s.num_of_models";
    $limit = "";

    $stream_db1 = pg_connect ("host=localhost port=5432 dbname=goto user=goto");
	$sql_a1 = array('\' \'');
	$sql_g = getG($groupby, $having, $orderby);

	$sql1 = getSQL($sql_a1, $orderby_atts, $table, $where, $sql_g, $limit, null, null);
    $result1 = pg_query($stream_db1, $sql1);

    //$i = 0;
    $j = 0;
    $pop_num = 0;
    $b = "";

$array1_1 = array();
while($row1 = pg_fetch_row($result1)){
	array_push($array1_1, array($row1[0]));
}

    for($i1=0; $i1<count($array1_1); $i1++){
          //$b .= str_replace('___SSQL_StreamFunc_CountLabel___', '_'.$i, $row[$j]);
          $b .= '<DIV Class="row">
<div class="TFE10007">
<DIV Class="row">
<div class="TFE10000">
</div>
</div>

<DIV Class="row">
<div class="TFE10005">
3018モデルベース</div>
</div>

<DIV Class="row">
<div class="TFE10006">
'.$array1_1[$i1][0].'
</div>
</div>

</div>
</div>
';
    }
    pg_close($stream_db1);

    $ret['result'] = $b;

    //header("Content-Type: application/json; charset=utf-8");
    echo json_encode($ret);


function getSQL($sql_a, $orderby_atts, $table, $where, $sql_g, $limit, $sql_a2, $row){
	$sql = getSF($sql_a, $orderby_atts, $table);
	if(is_null($sql_a2)){
		if($where != '')	$sql .= ' WHERE '.$where.' ';
		$sql .= $sql_g.' '.$limit;
	}else{
		$sql .= ' WHERE ';
		if($where != '')	$sql .= $where.' AND ';
		$sql .= getW($sql_a2, $row).$sql_g;
	}
	return $sql;
}
function getSF($sql_a, $orderby_atts, $table){
	return 'SELECT DISTINCT '.getAs($sql_a).$orderby_atts.' FROM '.$table;
}
function getAs($atts){
	$r = '';
	foreach($atts as $val){
    	$r .= getA($val).',';
    }
	return substr($r, 0, -1);
}
function getA($att){
	$sql_as = 'COALESCE(CAST(';
	$sql_ae = " AS varchar), '')";
	return $sql_as.$att.$sql_ae;
}
function getW($al, $ar){
	$r = '';
	$and = ' AND ';
	for($i=0 ; $i<count($al); $i++){
		$r .= $al[$i]." = '".$ar[$i]."'".$and;
	}
	return rtrim($r, $and);
}
function getG($groupby, $having, $orderby){
	$r = '';
	if($groupby != '')	$r .= ' GROUP BY '.$groupby.' ';
	if($having != '')	$r .= ' HAVING '.$having.' ';
	$r .= ' '.$orderby;
	return $r;
}

function checkHTMLsc($str){
	return htmlspecialchars($str, ENT_QUOTES, 'UTF-8');
}
?>

