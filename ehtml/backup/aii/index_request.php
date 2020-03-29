<?php
    header('Content-type: text/plain; charset= UTF-8');

	$ret = "";
	if (isset($_GET['tkey'])) {
		try {
			require_once('./Ssql/ssql.php');
			ssql_setConfig("./config.ssql");
		} catch ( Exception $ex ) { }


		// 画像のキャッシュ回避用
		function d(){	
		  return '?'.date("YmdHis");
		}	

		function get_SSQL_query($t){
			$query1 = "
				GENERATE HTML {
				       [null((desc1)s.num_of_models)!
				        s.num_of_models||'モデルベース'@{height=40, font-size=20, color=#6699FF, padding=2, align=center, valign=bottom}!
				       [ {plink(s.id||':  '||s.d@{height=30, font-size=13, padding=2, align=center}, './r_details.html', s.id)!
				          image(s.name||'/".$t.".png".d()."', './r')
				         }@{padding=8, align=center}! null((asc2)s.id)
				       ],3! !
				       ' '@{height=40}
				       ]!
				      } FROM stock_model s;
			";
			return $query1;
		}
		$ret = ssql_exec2(get_SSQL_query($_GET['tkey']));



		$ret = substr($ret, strpos($ret, "<script>"));
		$ret = str_replace("document.getElementsByTagName(\"head\")[0].appendChild", "$(\"head\").append", $ret);

		echo $ret;
	}else{
        echo 'Ajax request failed.';
    }
?>