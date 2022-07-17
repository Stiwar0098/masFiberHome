<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT * FROM modelosont where (nombre_modelosont='$filtrar' or id_modelosont ='$filtrar' or tipo_modelosont ='$filtrar') and estado_modelosont='activo'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>