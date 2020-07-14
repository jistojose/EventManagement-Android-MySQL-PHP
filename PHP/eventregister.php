<?php 
require "conn.php";


$eventname =$_POST["eventname"];
$name =$_POST["name"];
$email =$_POST["email"];
$phone =$_POST["phone"];
$address =$_POST["address"];
$state =$_POST["state"];
$postalcode =$_POST["postalcode"];
$price =$_POST["price"];




$sql_register = "INSERT INTO `event_registration` (`eventname`,`name`,`email`,`phone`,`address`,`state`,`postalcode`,`price`) VALUES ('$eventname','$name','$email','$phone','$address','$state','$postalcode','$price')";

	

if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Registered";
}else{
	echo "Failed to Register";
}

?>