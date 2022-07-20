<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT c.id_cajanivel1, c.nombre_cajanivel1, c.direccion_cajanivel1, c.referencia_cajanivel1, c.latitud_cajanivel1, c.longitud_cajanivel1, c.id_vlan,v.nombre_vlan, c.id_ciudad ,ci.nombre_ciudad,c.abreviatura_cajanivel1, c.cantidadhilos_cajanivel1, c.estado_cajanivel1 FROM cajanivel1 c inner JOIN vlan v on v.id_vlan=c.id_vlan INNER JOIN ciudad ci on c.id_ciudad=ci.id_ciudad where ( c.nombre_cajanivel1 = '$filtrar' or c.direccion_cajanivel1 = '$filtrar' or c.referencia_cajanivel1 = '$filtrar' or ci.nombre_ciudad = '$filtrar' or v.nombre_vlan = '$filtrar' ) and c.estado_cajanivel1='activo'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>