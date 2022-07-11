<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT id_ont FROM ont where serie_ont='$_GET[serie_ont]'");                
$result_register = mysqli_fetch_array($query);
$total_registro = $result_register['id_ont'];
$query_insert = mysqli_query($mysqli, "UPDATE rangoont set id_ont='$total_registro',estado_rangoont='$_GET[estado_rangoont]' where id_rangoont='$_GET[id_rangoont]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangoont-editar"}';
    }
mysqli_close($mysqli);
?>