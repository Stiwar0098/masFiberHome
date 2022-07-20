<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT * FROM usuario where (nombre_usuario like '%' or usuario_usuario LIKE '%') and estado_usuario='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT * FROM usuario where (nombre_usuario LIKE '$filtrar%' or usuario_usuario LIKE '$filtrar%') and estado_usuario='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>