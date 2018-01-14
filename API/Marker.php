<?php
require 'include/DB_CONNECT.php';

$db = new DB_CONNECT();
if(isset($_POST['medicine']))
{$response= array();
$product=array();
  $med_name = $_POST['medicine'];
   // $med_name='crocin';
  $query1="Show TABLES";

          $result = mysqli_query($db->connect(),$query1);

          if(mysqli_num_rows($result)>0)
          {
          while ($res = mysqli_fetch_array($result)) {
            # code...
            $table_name = $res['Tables_in_id2905562_shopname'] ;
            $query2 = "SELECT Name FROM $table_name WHERE Name = '$med_name' AND Qty >0 ";

            $result2 = mysqli_query($db->connect(),$query2);
            if($result2 != false){
            while($res2 = mysqli_fetch_array($result2))
              {   
                 
                 $query3 = "SELECT * FROM SHOPS WHERE Name = '$table_name' ";
                  $result3 = mysqli_query($db->connect(),$query3);
                 if(mysqli_num_rows($result3)>0)
                 {  
                     while($res3=mysqli_fetch_array($result3)){
                      $response['Name']= $table_name ;
                      $response['Location']= $res3['Location']; 
                      $response['Phone']= $res3['Phone']; 
                     }
                      
                      
                }
                   array_push($product,$response);

          }
              
          }
            
       
          }
           echo json_encode($product);
        }
          else {
            echo "Failed.";
          }
}

 ?>
