<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT c.id_cajanivel1, c.nombre_cajanivel1, c.direccion_cajanivel1, c.referencia_cajanivel1, c.latitud_cajanivel1, c.longitud_cajanivel1, c.id_vlan,v.nombre_vlan, c.id_ciudad ,ci.nombre_ciudad,c.abreviatura_cajanivel1, c.cantidadhilos_cajanivel1, c.estado_cajanivel1 FROM cajanivel1 c inner JOIN vlan v on v.id_vlan=c.id_vlan INNER JOIN ciudad ci on c.id_ciudad=ci.id_ciudad WHERE c.nombre_cajanivel1 like '%' or c.direccion_cajanivel1 like '%' or c.referencia_cajanivel1 like '%' or ci.nombre_ciudad like '%' or v.nombre_vlan like '%' and c.estado_cajanivel1='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT c.id_cajanivel1, c.nombre_cajanivel1, c.direccion_cajanivel1, c.referencia_cajanivel1, c.latitud_cajanivel1, c.longitud_cajanivel1, c.id_vlan,v.nombre_vlan, c.id_ciudad ,ci.nombre_ciudad,c.abreviatura_cajanivel1, c.cantidadhilos_cajanivel1, c.estado_cajanivel1 FROM cajanivel1 c inner JOIN vlan v on v.id_vlan=c.id_vlan INNER JOIN ciudad ci on c.id_ciudad=ci.id_ciudad WHERE c.nombre_cajanivel1 like '$filtrar%' or c.direccion_cajanivel1 like '$filtrar%' or c.referencia_cajanivel1 like '$filtrar%' or ci.nombre_ciudad like '$filtrar%' or v.nombre_vlan like '$filtrar%' and c.estado_cajanivel1='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>