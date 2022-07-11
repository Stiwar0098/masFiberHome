<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE vlan set numerovlan_vlan='$_GET[numerovlan_vlan]', nombre_vlan='$_GET[nombre_vlan]',numeroolt_vlan='$_GET[numeroolt_vlan]',tarjetaolt_vlan='$_GET[tarjetaolt_vlan]',puertoolt_vlan='$_GET[puertoolt_vlan]',ipinicio_vlan='$_GET[ipinicio_vlan]',ipfin_vlan='$_GET[ipfin_vlan]',mascara_vlan='$_GET[mascara_vlan]',gateway_vlan='$_GET[gateway_vlan]',estado_vlan='$_GET[estado_vlan]' where id_vlan='$_GET[id_vlan]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion"}';
    }
mysqli_close($mysqli);
?>