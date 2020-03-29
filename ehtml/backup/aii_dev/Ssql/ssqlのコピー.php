<?PHP
  $debug = true;
  $debug = false;

  $cacheFlag = false;
  $associative_array = "";
  $ret = "";

  session_start();

  //Cache_Liteインクルード
  require_once "./Ssql/Cache/Lite.php";

  function ssql_setConfig(){
    $_SESSION['num'] = 0;

    // 引数の数が1つだったら
    if(func_num_args() == 1){
      // configファイルのパス
      $_SESSION["config"] = func_get_arg(0);
    } else {
      switch (func_get_arg(0)) {
        case 'postgresql':
          $_SESSION["driver"] = func_get_arg(0);
          $_SESSION["db"] = func_get_arg(1);
          $_SESSION["host"] = func_get_arg(2);
          $_SESSION["user"] = func_get_arg(3);
          break;
        
        case 'sqlite':
          $_SESSION["driver"] = func_get_arg(0);
          $_SESSION["db"] = func_get_arg(1);
          $_SESSION["host"] = func_get_arg(2);
          break;

        default:
          # code...
          break;
      }
    }
  }

  // キャッシュを保持する時間を設定
  $_SESSION['ssql_cachetime'] = 3600;	//Default: 1時間
  function ssql_cacheTime($cachetime){
    $_SESSION['ssql_cachetime'] = $cachetime;
  }

  
  // DBの更新頻度の設定
  function ssql_frequency(){
    $_SESSION['frequency'] = func_get_arg(0);
  }

  function ssql_exec_base($query){
    global $debug;
    global $cacheFlag;
    global $associative_array;
    global $ret;
    $ret = "";
    $_SESSION['num']++;
    //$associative_array = debug_backtrace();


    //キャッシュオプション設定
    $cacheOptions = array(
      'cacheDir' => './Ssql/tmp/',  //tmpディレクトリに設定
      'caching'  => 'true',    // キャッシュを有効に
      'automaticSerialization' => 'true',    // 配列を保存可能に
      //'lifeTime' => '3600',    //キャッシュを保持する時間を1時間に設定
      'lifeTime' => $_SESSION['ssql_cachetime'],    //キャッシュを保持する時間を1時間に設定
    );

    //キャッシュID設定 → ファイル名+'Query'+ssql実行の連番
    $currentFileName = $associative_array[0]["file"];
    //$fileName = substr($currentFileName, strrpos($currentFileName, "/") + 1);
    //$file = substr($fileName, 0, strpos($fileName, "."));
    $id = $file.'Query'.$_SESSION['num'];

    //$ret .= var_dump($associative_array) . "<br>";
    //$ret .= $fileName . "<br>" . $file . "<br>" . $id;

    //Cache_Liteオブジェクト生成
    $Cache_Lite = new Cache_Lite($cacheOptions);
    // ここまでキャッシュの設定



    // link, parameter関数関係
    $flag = true;
    //$key = "att";
    $key = "ssql_ehtml_att";	//html
    $key0 = "att";		//xml
    $att_num = 1;
    while($flag){
      if(isset($_POST[$key.$att_num])){
        $array[$att_num-1] = $_POST[$key.$att_num];
      } else if(isset($_GET[$key.$att_num])){
        $array[$att_num-1] = $_GET[$key.$att_num];
      } else if(isset($_POST[$key0.$att_num])){
        $array[$att_num-1] = $_POST[$key0.$att_num];
      } else if(isset($_GET[$key0.$att_num])){
        $array[$att_num-1] = $_GET[$key0.$att_num];
      } else {
        $flag = false;
      }
      $att_num++;
    }

    $jarFilePath = "./Ssql/ssql.sh";
    if($_SESSION["driver"] == "postgresql"){
      $ssqlArgs = setDriver($_SESSION["driver"]).setDB($_SESSION["db"]).setHost($_SESSION["host"]).setUser($_SESSION["user"]);
    } else if($_SESSION["driver"] == "sqlite"){
      $ssqlArgs = setDriver($_SESSION["driver"]).setDB($_SESSION["db"]).setHost($_SESSION["host"]);
    } else if(isset($_SESSION["config"])){
      $ssqlArgs = setConfig($_SESSION["config"]);
    }

    // 値を受け取っていたら
    if(isset($array[0])){
      $ssqlArgs = $ssqlArgs.setValue($array);
    } 

    //$ssqlArgs = setOutFileName($associative_array).setOutdir().$ssqlArgs;
    $ssqlArgs = setOutFileName($currentFileName).setOutdir().$ssqlArgs;
    // $ssqlArgs = checkQueryCache($Cache_Lite, $query, $associative_array[0]["file"]).$ssqlArgs;
    $ssqlArgs = checkQueryCache($Cache_Lite, $query, $id).$ssqlArgs;
    $ssqlArgs = setExeNum($_SESSION['num']).$ssqlArgs;
    // echo $ssqlArgs.setQuery($query)."<br>";
    // echo $jarFilePath.$ssqlArgs.setQuery($query);
    $result = shell_exec('export LANG=ja_JP.UTF-8;'.$jarFilePath.$ssqlArgs.setQuery($query));
    if ($result) {
      $data = $Cache_Lite->get($id);
      // echo trim($result);

      if(trim($result) == "test"){
        // echo "XMLそのまま";
        if($debug)	$ret .= "XMLそのまま";
        $ret .= $data[1];
        // echo $ret;
      } else {
        // echo "XML更新";
        if($debug)      $ret .= "XML更新";
        $ret .= $result;
        // echo $ret;
      }
      // TODO キャッシュタイミング/処理の変更？ 2019/6/12
      // 保存するキャッシュの代入 -> クエリと出力結果のHTMLコード
      $cacheArray = array($query, $result);
      if($cacheFlag){
        saveCache($Cache_Lite, $cacheArray, $id);
      }
      // 下2つ
      // $data = $Cache_Lite->get($id);
      // echo $data[1];
    } else if ($result == false) {
      // echo "NG";
      $ret .= shell_exec('export LANG=ja_JP.UTF-8;'.$jarFilePath.setEhtml().$ssqlArgs.setQuery($query).' 2>&1');
      // echo $ret;
    }

    return $ret;
    // return $ret."\nOK ".strlen($result);
    // return $ret."\nOK ".strlen($result)." ".$result;

    // return $result;
  }
  function ssql_exec($query){
    global $associative_array;
    $associative_array = debug_backtrace();
    echo ssql_exec_base($query);
  }
  function ssql_exec2($query){
    global $associative_array;
    $associative_array = debug_backtrace();
    return ssql_exec_base($query);
  }



  function checkQueryCache($Cache_Lite, $query, $id){
    global $debug;
    global $cacheFlag;
    global $ret;
    if($data = $Cache_Lite->get($id)){
      // $data[0] -> クエリ
      // $data[1] -> HTMLコード
      //有効なキャッシュがある場合の処理
      // echo "キャッシュあり<br>";
      if($debug)      $ret .= "キャッシュあり<br>";
      // 有効なキャッシュ（クエリ内容）が引数のクエリと等しい場合は
      if($data[0] == $query){
        $cacheFlag = false;
        // echo "同じクエリ: インクリメンタル実行<br>";
        if($debug)      $ret .= "同じクエリ: インクリメンタル実行<br>";
        return setIncremental();
      } else {
        // echo "違うクエリ: 埋め込み実行<br>";
        if($debug)      $ret .= "違うクエリ: 埋め込み実行<br>";
        // キャッシュの更新
        $cacheFlag = true;
        // $Cache_Lite->save($query, $id);
        // saveCache($Cache_Lite, $query, $id);
        return setEhtml();
      }
    }else{
      //有効なキャッシュがない場合の処理
      // echo "キャッシュなし: 埋め込み実行<br>";
      if($debug)      $ret .= "キャッシュなし: 埋め込み実行<br>";
      //データをキャッシュ保存
      $cacheFlag = true;
      // $Cache_Lite->save($query, $id);
      // saveCache($Cache_Lite, $query, $id);
      return setEhtml();
    }
  }

  function checkUpdateCache(){
  }

  // Cache_Liteオブジェクトに保存
  function saveCache($Cache, $target, $id){
    // echo $target;
    // echo '<br>';
    // echo $id; 
    $Cache->save($target, $id);
  }

  function setQuery($query){
    // "を'に変換
    $query = str_replace("\"", "'", $query);
    $query = '"'.$query.'"';
    return " -query ".$query;
  }

  // ドライバーをセット
  function setDriver($driver){
    return " -driver ".$driver;
  }

  // DBをセット
  function setDB($db){
    return " -db ".$db;
  }

  // ホストをセット
  function setHost($host){
    return " -h ".$host;
  }

  // ユーザをセット
  function setUser($user){
    return " -u ".$user;
  } 

  // configファイルをセット
  function setConfig($config){
    // "/"が含まれている場合
    if(strpos($config, "/") !== false){
      $config = substr($config, strrpos($config, "/") + 1);
    } 
    return " -c ".dirname(dirname(__FILE__))."/".$config;
  }

  function setValue($array){
    return " -ehtmlarg {".implode(",", $array)."}";
  }

  // 出力先
  function setOutdir(){
    // Ssqlの親ディレクトリを返す
    return " -d ".dirname(dirname(__FILE__));
  }

  // cssの名前
  function setOutFileName($currentFileName){
    //return " -o ".$associative_array[0]["file"];
    return " -o ".$currentFileName;
  }

  function setEhtml(){
    return " -ehtml";
  }

  function setIncremental(){
    return " -incremental";
  }

  function setExeNum($num){
    return " -querynum ".$num;
  }

  session_destroy();
?>

