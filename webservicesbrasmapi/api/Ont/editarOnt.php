<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE ont set serie_ont='$_GET[serie_ont]',id_modelo='$_GET[id_modelo]', responsable_ont='$_GET[responsable_ont]', numeroont='$_GET[numeroont]', estado_ont='$_GET[estado_ont]' where id_ont ='$_GET[id_ont]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion editarOnt"}';
    }
mysqli_close($mysqli);
?>