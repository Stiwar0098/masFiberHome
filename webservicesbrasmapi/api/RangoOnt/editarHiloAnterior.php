<?php
header('Content-Type: application/json');
include "../conexion.php";     
$query_insert = mysqli_query($mysqli, "UPDATE rangoont set id_ont=null,estado_rangoont='desactivo' where id_rangoont='$_GET[id_rangoont]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangoont-editaranterior"}';
    }
mysqli_close($mysqli);
?>