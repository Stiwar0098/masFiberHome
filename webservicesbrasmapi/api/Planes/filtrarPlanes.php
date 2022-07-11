<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT id_planes ,nombre_planes, estado_planes FROM planes WHERE (nombre_planes like '%')and estado_planes='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT id_planes ,nombre_planes, estado_planes FROM planes WHERE (nombre_planes LIKE '$filtrar%')and estado_planes='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>