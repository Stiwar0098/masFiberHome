<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE cajanivel1 set nombre_cajanivel1='$_GET[nombre_cajanivel1]',abreviatura_cajanivel1='$_GET[abreviatura_cajanivel1]',direccion_cajanivel1='$_GET[direccion_cajanivel1]',referencia_cajanivel1='$_GET[referencia_cajanivel1]',latitud_cajanivel1='$_GET[latitud_cajanivel1]',longitud_cajanivel1='$_GET[longitud_cajanivel1]',id_vlan='$_GET[id_vlan]',id_ciudad='$_GET[id_ciudad]',cantidadhilos_cajanivel1='$_GET[cantidadhilos_cajanivel1]',estado_cajanivel1='$_GET[estado_cajanivel1]' where id_cajanivel1='$_GET[id_cajanivel1]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>