<?php
require 'include/DB_CONNECT.php';

$db = new DB_CONNECT();

$response= array();


  

  $query1="Show TABLES";

          $result = mysqli_query($db->connect(),$query1);

          if(mysqli_num_rows($result)>0)
          {
          while ($res = mysqli_fetch_array($result)) {
            # code...
            $table_name = $res['Tables_in_id2905562_shopname'] ;
            $query2 = "SELECT Name FROM $table_name";

            $result2 = mysqli_query($db->connect(),$query2);
            if($result2 != false){
            if(mysqli_num_rows($result2)>0)
              {
                while($res2 = mysqli_fetch_array($result2)){
                       
                    if(strcmp($table_name,'SHOPS') && strcmp($res2['Name'],'Name')){
                     array_push(  $response, $res2['Name']);
                    }
                  
                    
                   }
          }
          }
             
          }
             echo json_encode($response);
        }
          else {
            echo "Failed.";
          }

 ?>
