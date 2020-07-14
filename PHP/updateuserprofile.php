<?php 
require "conn.php";

$email =$_POST["email"];
$username =$_POST["username"];
$phone =$_POST["phone"];

	$sql_register = "UPDATE user SET username='$username', phone='$phone' WHERE email='$email'";

	
if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Changed";
}else{
	echo "Failed to Change";
}

?>