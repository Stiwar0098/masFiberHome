<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT c.id_ciudad, c.nombre_ciudad, c.id_provincia, pr.nombre_provincia, c.estado_ciudad FROM ciudad c INNER JOIN provincia pr on c.id_provincia=pr.id_provincia where (c.nombre_ciudad like '%' or pr.nombre_provincia LIKE '%' ) and c.estado_ciudad='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT c.id_ciudad, c.nombre_ciudad, c.id_provincia, pr.nombre_provincia, c.estado_ciudad FROM ciudad c INNER JOIN provincia pr on c.id_provincia=pr.id_provincia where (c.nombre_ciudad LIKE '$filtrar%' or pr.nombre_provincia LIKE '$filtrar%' ) and c.estado_ciudad='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>