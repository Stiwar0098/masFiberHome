<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM ciudad where nombre_ciudad='$_GET[nombre_ciudad]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO ciudad VALUES (0,'$_GET[nombre_ciudad]','$_GET[id_provincia]','$_GET[estado_ciudad]')");
        if($query_insert){
            echo '{"respuesta":"ok"}';
        }else{
            echo '{"respuesta":"error en la insercion"}';
        }
    }else{
        echo '{"respuesta":"La provincia ya existe"}';
    }    
mysqli_close($mysqli);
?>