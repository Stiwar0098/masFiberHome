<?php
header('Content-Type: application/json');
include "../conexion.php";          
$usuario=$_GET['usuario_cliente'];                     
$query = mysqli_query($mysqli,"SELECT * FROM cliente where usuario_cliente='$usuario'");                
$result = mysqli_num_rows($query);
if($result > 0){
    echo '[{"respuesta":"error"}]';    
}else{///no existen numeros de serviports eliminados
       echo '[{"respuesta":"ok"}]';    
}
mysqli_close($mysqli);
?>