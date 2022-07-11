<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT c2.id_cajanivel2, c2.nombre_cajanivel2, c2.abreviatura_cajanivel2, c2.direccion_cajanivel2, c2.referencia, c2.latitud_cajanivel2, c2.longitud_cajanivel2, c2.id_cajanivel1,c1.nombre_cajanivel1,c1.abreviatura_cajanivel1,v.id_vlan,c2.hilocajanivel1_cajanivel2,c2.cantidadhilos_cajanivel2, c2.estado_cajanivel2 FROM cajanivel2 c2 INNER JOIN cajanivel1 c1 on c2.id_cajanivel1=c1.id_cajanivel1 INNER JOIN vlan v on c1.id_vlan=v.id_vlan where (c2.nombre_cajanivel2 like '%' or c2.direccion_cajanivel2 like '%' or c2.referencia like '%' or c1.nombre_cajanivel1 like '%') and c2.estado_cajanivel2='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT c2.id_cajanivel2, c2.nombre_cajanivel2, c2.abreviatura_cajanivel2, c2.direccion_cajanivel2, c2.referencia, c2.latitud_cajanivel2, c2.longitud_cajanivel2, c2.id_cajanivel1,c1.nombre_cajanivel1,c1.abreviatura_cajanivel1,v.id_vlan,c2.hilocajanivel1_cajanivel2,c2.cantidadhilos_cajanivel2, c2.estado_cajanivel2 FROM cajanivel2 c2 INNER JOIN cajanivel1 c1 on c2.id_cajanivel1=c1.id_cajanivel1 INNER JOIN vlan v on c1.id_vlan=v.id_vlan where (c2.nombre_cajanivel2 like = '$filtrar%' or c2.direccion_cajanivel2 like = '$filtrar%' or c2.referencia like = '$filtrar%' or c1.nombre_cajanivel1 like = '$filtrar%') and c2.estado_cajanivel2='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>