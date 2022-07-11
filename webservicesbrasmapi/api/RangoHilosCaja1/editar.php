<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT id_cajanivel2 FROM cajanivel2 where nombre_cajanivel2='$_GET[nombre_cajanivel2]'");                
$result_register = mysqli_fetch_array($query);
$total_registro = $result_register['id_cajanivel2'];
$query_insert = mysqli_query($mysqli, "UPDATE rangohiloscaja1 set id_cajanivel2='$total_registro',estado_rangohiloscaja1='$_GET[estado_rangohiloscaja1]' where id_rangohiloscaja1='$_GET[id_rangohiloscaja1]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangoHilosCaja1-editar"}';
    }
mysqli_close($mysqli);
?>