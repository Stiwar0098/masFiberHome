<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query = mysqli_query($mysqli,"SELECT c.id_ont, c.id_cajanivel2,c.hilocajanivel2_cliente,v.id_vlan,c.ip_cliente from cliente c 
                                    INNER JOIN cajanivel2 c2 on c2.id_cajanivel2=c.id_cajanivel2 
                                    INNER JOIN cajanivel1 c1 on c2.id_cajanivel1=c1.id_cajanivel1 
                                    INNER JOIN vlan v on c1.id_vlan=v.id_vlan 
                                    where c.id_cliente='$_GET[id]'");    
    $result_register = mysqli_fetch_array($query);    
    $idont = $result_register['id_ont'];    
    $idcaja2 = $result_register['id_cajanivel2'];    
    $numerohilo = $result_register['hilocajanivel2_cliente'];
    $idvlan = $result_register['id_vlan'];    
    $ip = $result_register['ip_cliente'];    
    
    $query = mysqli_query($mysqli,"SELECT numeroont FROM ont where id_ont='$idont'");
    $result_register = mysqli_fetch_array($query);    
    $numeroont = $result_register['numeroont'];    

    $query_insert = mysqli_query($mysqli, "DELETE from cliente where id_cliente='$_GET[id]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){        
        $query_insert = mysqli_query($mysqli, "INSERT INTO idsclienteslibres VALUES (0,'$_GET[id]','activo')");        
        $query_insert = mysqli_query($mysqli, "DELETE from ont where id_ont='$idont'");
        $can=mysqli_affected_rows($mysqli);
        if($can==1){
            $query_insert = mysqli_query($mysqli, "UPDATE rangoont set id_ont=null,estado_rangoont='desactivo' where id_vlan='$idvlan' and id_ont='$idont' and numero_rangoont='$numeroont'");
            if($can==1){
                $query_insert = mysqli_query($mysqli, "UPDATE rangohiloscaja2 set id_cliente=null,estado_rangohiloscaja2='desactivo' where id_cajanivel2='$idcaja2' and id_cliente='$_GET[id]' and numero_rangohiloscaja2='$numerohilo'");
                if($can==1){                 
                    $query_insert = mysqli_query($mysqli, "UPDATE rangodireccionesip set id_cliente=null,estado_rangodireccionesip='desactivo' where id_vlan ='$idvlan' and id_cliente='$_GET[id]' and ip_rangodireccionesip='$ip'");   
                    if($can==1){
                        echo '{"respuesta":"ok"}';
                    }else{
                        echo '{"respuesta":"error en la modificacion eliminarServicio-rangodireccionesip 5"}';
                    }
                }else{
                    echo '{"respuesta":"error en la modificacion eliminarServicio-rangohiloscaja2 4 "}';
                }
            }else{
                echo '{"respuesta":"error en la modificacion eliminarServicio-rangoont 3 "}';
            }            

        }else{
            echo '{"respuesta":"error en la eliminacion eliminarServicio 2 "}';
        }
    }else{
        echo '{"respuesta":"error en la eliminacion eliminarServicio 1"}';
    }   
mysqli_close($mysqli);
?>