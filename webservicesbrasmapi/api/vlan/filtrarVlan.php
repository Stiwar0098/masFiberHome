<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT id_vlan,numerovlan_vlan, nombre_vlan, numeroolt_vlan, tarjetaolt_vlan, puertoolt_vlan, ipinicio_vlan, ipfin_vlan, mascara_vlan, gateway_vlan, estado_vlan FROM vlan WHERE (nombre_vlan like '%')and estado_vlan='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT id_vlan,numerovlan_vlan, nombre_vlan, numeroolt_vlan, tarjetaolt_vlan, puertoolt_vlan, ipinicio_vlan, ipfin_vlan, mascara_vlan, gateway_vlan, estado_vlan FROM vlan WHERE (nombre_vlan LIKE '$filtrar%')and estado_vlan='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>