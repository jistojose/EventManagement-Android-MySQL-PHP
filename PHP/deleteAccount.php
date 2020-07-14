<?php 
require "conn.php";

$email =$_POST["email"];


	$sql_register = "DELETE FROM `user` WHERE email='$email'";

	
if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Deleted";
}else{
	echo "Failed to Delete";
}

?>