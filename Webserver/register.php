<?php
    // response json
    $json = array();
     
    /**
     * Registering a user device
     * Store reg id in users table
     */
    if (isset($_POST["name"]) && isset($_POST["regId"])) {
        $name = $_POST["name"];
        $passw = "null";
        $gcm_regid = $_POST["regId"]; // GCM Registration ID
        // Store user details in db
        include_once './db_functions.php';
        include_once './GCM.php';
     
        $db = new DB_Functions();
        $gcm = new GCM();
     
        $res = $db->storeUser($name, $passw, $gcm_regid);
     
        $reg_ids = array($gcm_regid);
        $message = array("req_type" => "RegOK");
     
        $result = $gcm->send_notification($reg_ids, $message);
     
        echo $result;
    } else {
        // user details missing
    }
?>