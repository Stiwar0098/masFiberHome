<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT * FROM modelosont WHERE (nombre_modelosont like '%' or tipo_modelosont like '%')and estado_modelosont='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT * FROM modelosont WHERE (nombre_modelosont LIKE '$filtrar%' or tipo_modelosont like '$filtrar%')and estado_modelosont='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>