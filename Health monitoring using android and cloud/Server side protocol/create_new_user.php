<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['name']) && isset($_POST['address']) && isset($_POST['aadhar']) && isset($_POST['gender']) && isset($_POST['dob'])) {
 
    $name = $_POST['name'];
    $address = $_POST['address'];
    $aadhar = $_POST['aadhar'];
    $gender = $_POST['gender'];
    $dob = $_POST['dob'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO General_info(Name,DOB,Address,Aadhar_no,Sex,Time) VALUES('$name', '$dob', '$address','$aadhar','$gender',now())");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "New user successfully added.";

        $result_id = mysql_query("SHOW TABLE STATUS WHERE `Name` = 'General_info'");
        $data = mysql_fetch_assoc($result_id);
        $next_increment = $data['Auto_increment'];

        $response["online_id"] = $next_increment;
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>