<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

require "conn.php";

 $CategoryName= $_POST['CategoryName'];



$sql = "SELECT * FROM event where name = '$CategoryName'" ;

$result = $conn->query($sql);

if ($result->num_rows >0) {
 
 
 while($row[] = $result->fetch_assoc()) {
 
 $tem = $row;
 
 $json = json_encode($tem);
 
 }
 
} else {
 echo "No Results Found.";
}
 echo $json;

$conn->close();
}
?>