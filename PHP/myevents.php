
<?php

include 'conn.php';


if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
$sql = "SELECT * from event";

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

