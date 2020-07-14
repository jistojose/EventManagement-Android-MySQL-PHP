<?php
require "conn.php";


$email=$_POST["email"];
$password=$_POST["pass"];

#$email ="businessassociate1iiitk@gmail.com";
#$password ="jistojose";

$isValidEmail = filter_var($email,FILTER_VALIDATE_EMAIL);
if($conn){
if($isValidEmail === false){
	echo "This Email is not valid";
}else{
$sqlCheckEmail = "SELECT * FROM `user` WHERE email='$email'";
	$useremailQuery = mysqli_query($conn,$sqlCheckEmail);
	if(!$useremailQuery || (mysqli_num_rows($useremailQuery)> 0)){
		//if(mysqli_num_rows($useremailQuery)> 0){
		 
		 $sqlLogin = "SELECT * FROM `user` WHERE `email` LIKE '$email' AND `password` LIKE '$password'";
		 $loginQuery = mysqli_query($conn,$sqlLogin);
		if(!$loginQuery || (mysqli_num_rows($loginQuery)> 0)){
			  //if(mysqli_num_rows($loginQuery)> 0){
		 echo "Login Success";
		 session_start();
   $_SESSION['email'] = $email;
    #$_SESSION['uid'] = $uid;

		 #echo($email);
		 #echo($password);
		 }
		 else{
		 echo "Wrong Password";
		 }
		 
		 }
		 else{
		echo "This Email is not registered"; 
		
		
}
}

}
else{
echo "Connection error";

}
?>