<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"call obtenerNumeroHiloCaja1Auto('$_GET[id_cajanivel1]')");                            
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}else{
    echo '{"respuesta":"error al obtener numero hilo automatico"}';    
}   
?>