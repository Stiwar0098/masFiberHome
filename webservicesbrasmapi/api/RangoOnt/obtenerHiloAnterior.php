<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT * FROM rangoont r2 WHERE r2.id_vlan='$_GET[id_vlan]' and r2.id_ont='$_GET[id_ont]'");                   
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