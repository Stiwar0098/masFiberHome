<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT * FROM vlan where nombre_vlan='$_GET[nombre_vlan]'");
    $result = mysqli_num_rows($query);
    if($result < 1){
        $query_insert = mysqli_query($mysqli, "INSERT INTO vlan VALUES (0,'$_GET[numerovlan_vlan]','$_GET[nombre_vlan]','$_GET[numeroolt_vlan]','$_GET[tarjetaolt_vlan]','$_GET[puertoolt_vlan]','$_GET[ipinicio_vlan]','$_GET[ipfin_vlan]','$_GET[mascara_vlan]','$_GET[gateway_vlan]','$_GET[estado_vlan]')");        
        if($query_insert){            
            $query = mysqli_query($mysqli,"call crearIpsDeLaVlan('$_GET[buckle2]','$_GET[ip_rangodireccionesip]','$_GET[nombre_vlan]')");                
            if($query){
                echo '{"respuesta":"ok"}';
            }else{
                echo '{"respuesta":"error en la insercion"}';    
            }
        }else{
            echo '{"respuesta":"error en la insercion"}';
        }
    }else{
        echo '{"respuesta":"La vlan ya existe"}';
    }    
mysqli_close($mysqli);
?>