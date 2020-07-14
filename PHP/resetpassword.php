
<?php 
require "conn.php";

$email =$_POST["email"];
$password =$_POST["pass"];
$confirmpassword =$_POST["confirmpass"];

	$sql_register = "UPDATE user SET password='$password', confirmpassword='$confirmpassword' WHERE email='$email'";

	
if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Changed";
}else{
	echo "Failed to Change";
}

?>
