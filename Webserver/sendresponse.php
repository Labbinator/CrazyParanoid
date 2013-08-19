<?php

	//Check if post data recived
	if(!isset($_POST["regid"]) || !isset($_POST["response"]) || !isset($_POST["alias"]) || !isset($_POST["data"])){
		die("Post data not set");
	}else if(($_POST["regid"]) == null || $_POST["response"] == null ||$_POST["alias"] == null || $_POST["data"] == null){
		die("One or more post data == null");
	}/*else{
		//ALL OK!!!
	}*/

	include_once './GCM.php';
	include_once './logger.php';	

	$response = $_POST["response"];
	$gcm_regid = $_POST["regid"];
	$alias = $_POST["alias"];
	$data = $_POST["data"];

	$gcm = new GCM();

	Logger::log("$gcm_regid $response $data");

	if($gcm_regid != null){

		$reg_ids = array($gcm_regid);

		$message = array("alias" => $alias, "response" => $response, "data" => $data);

		$result = $gcm->send_notification($reg_ids, $message);

		echo $result;
	}
?>