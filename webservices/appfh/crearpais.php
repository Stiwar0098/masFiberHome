<?php
include "conexion.php"; 
$nombre=$_GET['nombre'];
$estado=$_GET['estado'];
$query_insert = mysqli_query($mysqli, "INSERT INTO pais VALUES (null,'$nombre','$estado')");
mysqli_close($mysqli);
?>