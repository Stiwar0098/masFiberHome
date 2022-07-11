<?php
header('Cclienteent-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT id_cliente FROM cliente where usuario_cliente='$_GET[usuario_cliente]'");                
$result_register = mysqli_fetch_array($query);
$total_registro = $result_register['id_cliente'];
$query_insert = mysqli_query($mysqli, "UPDATE rangodireccionesip set id_cliente='$total_registro',estado_rangodireccionesip='$_GET[estado_rangodireccionesip]' where id_rangodireccionesip='$_GET[id_rangodireccionesip]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangodireccionesip-editar"}';
    }
mysqli_close($mysqli);
?>