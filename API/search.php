<?php
require 'include/DB_CONNECT.php';

$db = new DB_CONNECT();
if(isset($_POST['medicine']))
{$response= array();
  $med_name = $_POST['medicine'];

  $query1="Show TABLES";

          $result = mysqli_query($db->connect(),$query1);

          if(mysqli_num_rows($result)>0)
          {
          while ($res = mysqli_fetch_array($result)) {
            # code...
            $table_name = $res['Tables_in_id2905562_shopname'] ;
            $query2 = "SELECT Name FROM $table_name WHERE Name = '$med_name' AND Qty >0 ";

            $result2 = mysqli_query($db->connect(),$query2);
            if(mysqli_num_rows($result2)>0)
              array_push(  $response, $res['Tables_in_id2905562_shopname']);
          }
             echo json_encode($response);
          }
          else {
            echo "Failed.";
          }
}
 ?>
