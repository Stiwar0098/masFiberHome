<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT id_cliente FROM cliente where usuario_cliente='$_GET[usuario_cliente]'");                
$result_register = mysqli_fetch_array($query);
$total_registro = $result_register['id_cliente'];
$query_insert = mysqli_query($mysqli, "UPDATE rangohiloscaja2 set id_cliente='$total_registro',estado_rangohiloscaja2='$_GET[estado_rangohiloscaja2]' where id_rangohiloscaja2='$_GET[id_rangohiloscaja2]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangoHilosCaja2-editar"}';
    }
mysqli_close($mysqli);
?>