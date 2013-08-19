<?
	error_reporting(E_ALL);
	ini_set('display_errors', '1');

	//Check if post data recived
	if(!isset($_POST["alias"]) || !isset($_POST["passw"]) || !isset($_POST["regid"]) || !isset($_POST["req_type"])){
		die("Post data not set");
	}else if($_POST["alias"] == null || $_POST["passw"] == null || ($_POST["regid"]) == null || $_POST["req_type"] == null){
		die("One or more post data == null");
	}/*else{
		//ALL OK!!!
	}*/

	include_once './db_functions.php';
	include_once './GCM.php';
	include_once './logger.php';	

	$alias = $_POST["alias"];
	$pass = $_POST["passw"];
	$reqType = $_POST["req_type"];
	$regid = $_POST["regid"];

	$db = new DB_Functions();
	$gcm = new GCM();

	$gcm_regid = $db->getID($alias);

	echo "$gcm_regid $alias $pass $reqType $regid";

	Logger::log("$gcm_regid $alias $pass $reqType $regid");

	if($gcm_regid != null){

		$reg_ids = array($gcm_regid);

		$message = array("req_type" => $reqType, "passw" => $pass, "regid" => $regid);

		$result = $gcm->send_notification($reg_ids, $message);

		echo $result;
	}


?>