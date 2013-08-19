<?php

     class DB_Functions {
     
        private $db;
     
        //put your code here
        // constructor
        function __construct() {
            include_once './db_connect.php';
            // connecting to database
            $this->db = new DB_Connect();
            $this->db->connect();
        }
     
        // destructor
        function __destruct() {
             
        }
     
        /**
         * Storing new user
         * returns user details
         */
        public function storeUser($name, $passw, $gcm_regid) {
            // insert user into database
            $result = mysql_query("INSERT INTO gcm_users(gcm_regid, name, passw_hash, created_at) VALUES('$gcm_regid', '$name', '$passw', NOW())");
            // check for successful store

            if ($result) {
                // get user details
                $id = mysql_insert_id(); // last inserted id
                $result = mysql_query("SELECT * FROM gcm_users WHERE id = $id") or die(mysql_error());
                // return user details
                if (mysql_num_rows($result) > 0) {
                    return mysql_fetch_array($result);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
     
        /**
         * Getting all users
         */
        public function getAllUsers() {
            $result = mysql_query("select * FROM gcm_users");
            return $result;
        }


        public function getID($alias='') {

            //Will be false if no match found in db
            if($result = mysql_query("SELECT * FROM gcm_users WHERE name = '$alias'")){

                if(mysql_num_rows($result) < 2) {
                    $arrayName = mysql_fetch_assoc($result);

                    return $arrayName['gcm_regid'];
                }
            }
        echo "<br>No match found in db...<br>";
        return null;
        }
    }
?>