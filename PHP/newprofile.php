
<?php

#session_start(); // this NEEDS TO BE AT THE TOP of the page before any output etc
  #$email=$_SESSION['email']; 
   #$email="sam@gmail.com";

	   
  

// now you can do whatever you like
#echo $_SESSION['email'];

include 'conn.php';

/*ob_start();
include 'newlogin.php';
$email= $_GET["email"];
ob_end_clean();

*/


$email="sam@gmail.com";
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "SELECT * FROM user where email='$email'";
$result = $conn->query($sql);

$json = array();

if ($result->num_rows > 0) {

    // output data of each row
    while($row[] = $result->fetch_assoc()) {

       $json = json_encode($row);
	   print_r($json);


    }
} else {
    echo "0 results";
}

echo $json;
#print_r($json);
$conn->close();


?>

