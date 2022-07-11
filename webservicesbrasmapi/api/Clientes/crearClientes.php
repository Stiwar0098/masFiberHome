<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM clientepersona where cedula_clientepersona='$_GET[cedula_clientepersona]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO clientepersona VALUES (0,'$_GET[cedula_clientepersona]','$_GET[nombre_clientepersona]','$_GET[apellido_clientepersona]','$_GET[correo_clientepersona]','$_GET[telefono1_clientepersona]','$_GET[telefono2_clientepersona]','$_GET[estado_clientepersona]')");        
        if($query_insert){                        
            echo '{"respuesta":"ok"}';           
        }else{
            echo '{"respuesta":"error en la insercion"}';
        }
    }else{
        echo '{"respuesta":"el modelo ya existe"}';
    }    
mysqli_close($mysqli);
?>