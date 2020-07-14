
<?php

include 'conn.php';



//$email="demo@gmail.com";
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
#$sql = "SELECT * from event";
$sql = "SELECT event.id,event.name,event.ewhere,event.eventstart,event.eventends,event.speaker,event.price,user.username,user.phone FROM user INNER JOIN event ON user.email = event.email;";
$result = $conn->query($sql);

$json = array();

if ($result->num_rows > 0) {

    // output data of each row
    while($row[] = $result->fetch_assoc()) {

       $json = json_encode($row);


    }
} else {
    echo "0 results";
}
echo $json;
$conn->close();

//function eventdetails(){
	


?>

