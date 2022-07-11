<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT pr.id_provincia,pr.nombre_provincia,pr.id_pais,pa.nombre_pais,pr.estado_provincia FROM provincia pr INNER JOIN pais pa on pa.id_pais=pr.id_pais where pr.nombre_provincia='$filtrar' or pr.provincia_provincia='$filtrar' and pr.estado_provincia='activo'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>