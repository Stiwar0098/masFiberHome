<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE usuario set nombre_usuario='$_GET[nombre]',usuario_usuario='$_GET[usuario]',contraseña_usuario='$_GET[contrasena]',id_rol='$_GET[rol]',estado_usuario='$_GET[estado]' where id_usuario='$_GET[id]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>