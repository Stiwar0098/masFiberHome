<?php
header('Content-Type: application/json');
include "conexion.php";     
    $query_insert = mysqli_query($mysqli, "INSERT INTO pais(id_pais,nombre_pais,estado_pais) VALUES (0,'$_GET[nombre]','$_GET[estado]')");
    if($query_insert){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la insercion"}';
    }
mysqli_close($mysqli);
?>