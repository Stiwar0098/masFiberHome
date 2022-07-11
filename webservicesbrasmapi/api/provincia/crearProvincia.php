<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM provincia where nombre_provincia='$_GET[nombre_provincia]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO provincia VALUES (0,'$_GET[nombre_provincia]','$_GET[id_pais]','$_GET[estado_provincia]')");
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