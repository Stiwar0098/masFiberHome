<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE cajanivel2 set nombre_cajanivel2='$_GET[nombre_cajanivel2]',abreviatura_cajanivel2='$_GET[abreviatura_cajanivel2]',direccion_cajanivel2='$_GET[direccion_cajanivel2]',referencia='$_GET[referencia]',latitud_cajanivel2='$_GET[latitud_cajanivel2]',longitud_cajanivel2='$_GET[longitud_cajanivel2]',id_cajanivel1='$_GET[id_cajanivel1]',hilocajanivel1_cajanivel2='$_GET[hilocajanivel1_cajanivel2]',cantidadhilos_cajanivel2='$_GET[cantidadhilos_cajanivel2]',estado_cajanivel2='$_GET[estado_cajanivel2]' where id_cajanivel2='$_GET[id_cajanivel2]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion editar-cajanivel2"}';
    }
mysqli_close($mysqli);
?>