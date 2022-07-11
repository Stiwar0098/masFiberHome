<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT * FROM clientepersona WHERE (cedula_clientepersona like '%' or nombre_clientepersona like '%' or apellido_clientepersona like '%')and estado_clientepersona='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT * FROM clientepersona WHERE (cedula_clientepersona LIKE '$filtrar%' or nombre_clientepersona like '$filtrar%' or apellido_clientepersona like '$filtrar%')and estado_clientepersona='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>