<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE provincia set nombre_provincia='$_GET[nombre_provincia]',id_pais='$_GET[id_pais]',estado_provincia='$_GET[estado_provincia]' where id_provincia='$_GET[id_provincia]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>