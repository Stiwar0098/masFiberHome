<?php
include "conexion.php"; 
$filtrar=$_GET['filtrar'];
$query = mysqli_query($mysqli,"SELECT * FROM `pais` where nombre_pais LIKE '%$filtrar%'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_array($query)){
        echo json_encode($data);	
    }
}
?>