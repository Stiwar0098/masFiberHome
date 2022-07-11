<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT * FROM rangohiloscaja2 r2 WHERE r2.id_cajanivel2='$_GET[id_cajanivel2]' and r2.id_cliente='$_GET[id_cliente]'");                   
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