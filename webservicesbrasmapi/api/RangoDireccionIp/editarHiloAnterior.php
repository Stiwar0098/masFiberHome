<?php
header('Content-Type: application/json');
include "../conexion.php";     
$query_insert = mysqli_query($mysqli, "UPDATE rangodireccionesip set id_cliente=null,estado_rangodireccionesip='desactivo' where id_rangodireccionesip='$_GET[id_rangodireccionesip]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangodireccionesip-editarhiloanterior"}';
    }
mysqli_close($mysqli);
?>