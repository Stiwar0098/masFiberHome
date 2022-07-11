<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "DELETE from cajanivel2 where id_cajanivel2='$_GET[id]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la eliminacion "}';
    }   
mysqli_close($mysqli);
?>