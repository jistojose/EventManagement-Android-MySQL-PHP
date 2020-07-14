
<?php 
require "conn.php";


$name =$_POST["name"];
$eventstart =$_POST["eventstart"];
$eventends =$_POST["eventends"];
$ewhere =$_POST["ewhere"];
$type =$_POST["type"];
$category =$_POST["category"];
$speaker =$_POST["speaker"];
$price =$_POST["price"];
$mode =$_POST["mode"];
$photo =$_POST["photo"];
$description =$_POST["description"];
$email =$_POST["email"];

/*
$name ="blockchain";
$eventstart ="06/08/97";
$eventends ="06/08/97";
$ewhere ="kottayam";
$type ="tickets";
$category ="college";
$mode ="public";
$photo ="";
*/



	$sql_register = "INSERT INTO `event` (`name`,`eventstart`,`eventends`,`ewhere`,`type`,`category`,`speaker`,`price`,`mode`,`photo`,`description`,`email`) VALUES ('$name','$eventstart','$eventends','$ewhere','$type','$category','$speaker','$price','$mode','$photo','$description','$email')";

	

if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Created";
}else{
	echo "Failed to Create";
}

?>
