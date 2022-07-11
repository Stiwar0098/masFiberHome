<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM planes where nombre_planes='$_GET[nombre_planes]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO planes VALUES (0,'$_GET[nombre_planes]','$_GET[estado_planes]')");        
        if($query_insert){                        
            echo '{"respuesta":"ok"}';           
        }else{
            echo '{"respuesta":"error en la insercion"}';
        }
    }else{
        echo '{"respuesta":"La plan ya existe"}';
    }    
mysqli_close($mysqli);
?>