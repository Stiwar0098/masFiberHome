<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM cajanivel1 where nombre_cajanivel1='$_GET[nombre_cajanivel1]' or id_vlan='$_GET[id_vlan]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO cajanivel1 VALUES (0,'$_GET[nombre_cajanivel1]','$_GET[abreviatura_cajanivel1]','$_GET[direccion_cajanivel1]','$_GET[referencia_cajanivel1]','$_GET[latitud_cajanivel1]','$_GET[longitud_cajanivel1]','$_GET[id_vlan]','$_GET[id_ciudad]','$_GET[cantidadhilos_cajanivel1]','$_GET[estado_cajanivel1]')");
        if($query_insert){            
            $query = mysqli_query($mysqli,"call crearRangoHilosCaja1('$_GET[cantidadhilos_cajanivel1]','$_GET[nombre_cajanivel1]')");                
            if($query){
                echo '{"respuesta":"ok"}';
            }else{
                echo '{"respuesta":"error en la insercion2"}';    
            }
        }else{
            echo '{"respuesta":"error en la insercion1"}';
        }
    }else{
        echo '{"respuesta":"La caja ya existe o vlan ocupada"}';
    }     
mysqli_close($mysqli);
?>