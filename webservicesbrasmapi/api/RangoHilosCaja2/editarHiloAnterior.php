<?php
header('Content-Type: application/json');
include "../conexion.php";     
$query_insert = mysqli_query($mysqli, "UPDATE rangohiloscaja2 set id_cliente=null,estado_rangohiloscaja2='desactivo' where id_rangohiloscaja2='$_GET[id_rangohiloscaja2]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion rangoHilosCaja2-editarhiloanterior"}';
    }
mysqli_close($mysqli);
?>