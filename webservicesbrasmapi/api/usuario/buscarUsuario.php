<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT * FROM usuario where nombre_usuario='$filtrar' or usuario_usuario='$filtrar' and estado_usuario='activo'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>