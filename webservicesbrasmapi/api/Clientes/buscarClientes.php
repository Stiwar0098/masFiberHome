<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT * FROM clientepersona where (cedula_clientepersona='$filtrar' or id_clientepersona ='$filtrar') and estado_clientepersona='activo'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>