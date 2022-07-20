<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT * FROM pais where (nombre_pais like '%') and estado_pais='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT * FROM pais where (nombre_pais LIKE '$filtrar%') and estado_pais='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>