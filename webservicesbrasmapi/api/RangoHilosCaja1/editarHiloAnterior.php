<?php
header('Content-Type: application/json');
include "../conexion.php";     
$query_insert = mysqli_query($mysqli, "UPDATE rangohiloscaja1 set id_cajanivel2=null,estado_rangohiloscaja1='desactivo' where id_rangohiloscaja1='$_GET[id_rangohiloscaja1]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion hiloantetiorcaja1"}';
    }
mysqli_close($mysqli);
?>