<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE ciudad set nombre_ciudad='$_GET[nombre_ciudad]',id_provincia='$_GET[id_provincia]',estado_ciudad='$_GET[estado_ciudad]' where id_ciudad='$_GET[id_ciudad]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>