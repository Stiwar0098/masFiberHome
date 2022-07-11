<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM ont where serie_ont='$_GET[serie_ont]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO ont VALUES (0,'$_GET[serie_ont]','$_GET[id_modelo]','$_GET[responsable_ont]','$_GET[numeroont]','$_GET[estado_ont]')");        
        if($query_insert){                        
            echo '{"respuesta":"ok"}';           
        }else{
            echo '{"respuesta":"error en la insercion crearOnt"}';
        }
    }else{
        echo '{"respuesta":"la ont ya existe"}';
    }    
mysqli_close($mysqli);
?>