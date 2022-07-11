<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE planes set nombre_planes='$_GET[nombre_planes]', estado_planes='$_GET[estado_planes]' where id_planes ='$_GET[id_planes]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>