
<?php 
require "conn.php";


$name =$_POST["name"];
$username =$_POST["username"];
$email =$_POST["email"];
$phone =$_POST["phone"];
$password =$_POST["pass"];
$confirmpassword =$_POST["confirmpass"];

/*
$name ="jisto";
$username ="jistojose1";
$email ="businessassociate1iiiiuitk@gmail.com";
$phone ="25445522";
$password ="jistojose";
$confirmpassword = "jistojose";
*/


$isValidEmail = filter_var($email,FILTER_VALIDATE_EMAIL);
if(strlen($password)>40||strlen($password)<6){
	echo "Password must be less than 40 and more than 6 characters";
}else if($isValidEmail === false){
	echo "This Email is not valid";
}
else{
	
	$sqlCheckEmail = "SELECT * FROM `user` WHERE email='$email'";
	$useremailQuery = mysqli_query($conn,$sqlCheckEmail);
	
	if(!$useremailQuery || (mysqli_num_rows($useremailQuery)> 0)){
		echo "This Email is Already Registered, Type another Email";    
	}
    else{
	$sql_register = "INSERT INTO `user` (`name`,`username`,`email`,`phone`,`password`,`confirmpassword`) VALUES ('$name','$username','$email','$phone','$password','$confirmpassword')";

	}
}
if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Registered";
}else{
	echo "Failed to Register";
}

?>
