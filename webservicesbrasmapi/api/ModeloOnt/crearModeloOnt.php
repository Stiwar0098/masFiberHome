<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM modelosont where nombre_modelosont='$_GET[nombre_modelosont]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO modelosont VALUES (0,'$_GET[nombre_modelosont]','$_GET[tipo_modelosont]','$_GET[estado_modelosont]')");        
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