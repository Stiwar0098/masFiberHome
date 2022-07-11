<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"call validarNumeroOntManual('$_GET[id_vlan]','$_GET[numero_rangoont]')");                   
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}else{
    echo '[{"respuesta":"error"}]';
}
?>