<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE modelosont set nombre_modelosont='$_GET[nombre_modelosont]',tipo_modelosont='$_GET[tipo_modelosont]', estado_modelosont='$_GET[estado_modelosont]' where id_modelosont ='$_GET[id_modelosont]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion modeloOnt"}';
    }
mysqli_close($mysqli);
?>