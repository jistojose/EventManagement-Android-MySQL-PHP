
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

	$sql_register = "UPDATE event SET name='$name',eventstart='$eventstart',eventends='$eventends',ewhere='$ewhere', type='$type',category='$category',speaker='$speaker',price='$price',mode='$mode',photo='$photo',description='$description',email='$email' WHERE email='$email'";

	
if(mysqli_query($conn,$sql_register)){
	echo "Sucessfully Changed";
}else{
	echo "Failed to Change";
}

?>
