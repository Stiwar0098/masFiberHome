<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "DELETE from cajanivel1 where id_cajanivel1='$_GET[id]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la eliminacion "}';
    }   
mysqli_close($mysqli);
?>