<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE clientepersona set cedula_clientepersona='$_GET[cedula_clientepersona]',nombre_clientepersona='$_GET[nombre_clientepersona]', apellido_clientepersona='$_GET[apellido_clientepersona]', correo_clientepersona='$_GET[correo_clientepersona]', telefono1_clientepersona='$_GET[telefono1_clientepersona]', telefono2_clientepersona='$_GET[telefono2_clientepersona]', estado_clientepersona='$_GET[estado_clientepersona]' where id_clientepersona ='$_GET[id_clientepersona]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>