<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM usuario where usuario_usuario='$_GET[usuario]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO usuario VALUES (0,'$_GET[nombre]','$_GET[usuario]','$_GET[contrasena]','$_GET[rol]','$_GET[estado]')");
        if($query_insert){
            echo '{"respuesta":"ok"}';
        }else{
            echo '{"respuesta":"error en la insercion"}';
        }
    }else{
        echo '{"respuesta":"El usuario ya existe"}';
    }    
mysqli_close($mysqli);
?>