<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM cajanivel2 where nombre_cajanivel2='$_GET[nombre_cajanivel2]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO cajanivel2 VALUES (0,'$_GET[nombre_cajanivel2]','$_GET[abreviatura_cajanivel2]','$_GET[direccion_cajanivel2]','$_GET[referencia]','$_GET[latitud_cajanivel2]','$_GET[longitud_cajanivel2]','$_GET[id_cajanivel1]','$_GET[hilocajanivel1_cajanivel2]','$_GET[cantidadhilos_cajanivel2]','$_GET[estado_cajanivel2]')");
        if($query_insert){            
            $query = mysqli_query($mysqli,"call crearRangoHilosCaja2('$_GET[cantidadhilos_cajanivel2]','$_GET[nombre_cajanivel2]')");                
            if($query){
                echo '{"respuesta":"ok"}';
            }else{
                echo '{"respuesta":"error en la insercion2"}';    
            }
        }else{
            echo '{"respuesta":"error en la insercion1"}';
        }
    }else{
        echo '{"respuesta":"La caja ya existe"}';
    }     
mysqli_close($mysqli);
?>