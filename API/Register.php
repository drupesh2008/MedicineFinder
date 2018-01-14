<?php
require 'include/DB_CONNECT.php';

$db = new DB_CONNECT();

if(isset($_POST['Shopname']) && isset($_POST['license']) && isset($_POST['location']) && isset($_POST['Phone']))
{ 
  $response= array();
  
  $shop_name = $_POST['Shopname'];
  $license = $_POST['license'];
  $location = $_POST['location'];
  $phone = $_POST['Phone'];

          $query1 = "CREATE TABLE $shop_name(Id INT , Name VARCHAR(30), Qty INT, PRIMARY KEY (Id)) ";

          $query2 = "INSERT INTO SHOPS(Name,License_No,Location,Phone) VALUES('$shop_name','$license','$location','$phone')";

          $result = mysqli_query($db->connect(),$query1);
          $result2 = mysqli_query($db->connect(),$query2);

          // if(mysqli_num_rows($result)>0)
          // {
          // while ($res = mysqli_fetch_array($result)) {
          //   # code...
          //   $table_name = $res['Tables_in_id2905562_shopname'] ;
          //   $query2 = "SELECT Name FROM $table_name WHERE Name = '$med_name' AND Qty >0 ";

          //   $result2 = mysqli_query($db->connect(),$query2);
          //   if(mysqli_num_rows($result2)>0)
          //     array_push(  $response, $res['Tables_in_id2905562_shopname']);
          // }
          //    echo json_encode($response);
          // }
        
             echo json_encode(false);
        }
          else {
            echo json_encode(true);
          }

 ?>
