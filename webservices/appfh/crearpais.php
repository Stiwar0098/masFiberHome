<?php
include "conexion.php"; 
if($_SERVER['REQUEST_METHOD']=='PUT'){
    $datos = json_decode(file_get_contents('php://input'));
if($datos!=null){
    $nombre=$datos->nombre;
    $estado=$datos->estado;
    $query_insert = mysqli_query($mysqli, "INSERT INTO pais VALUES (null,'$nombre','$estado')");
}
}else{
    echo 'no put'
}

mysqli_close($mysqli);
?>