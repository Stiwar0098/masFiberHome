<?php
header('Content-Type: application/json');
include "../conexion.php";                           
$query = mysqli_query($mysqli,"SELECT * FROM idsclienteslibres");                
$result = mysqli_num_rows($query);
if($result > 0){
    $query = mysqli_query($mysqli,"SELECT min(numero_idsclienteslibres) as 'numero' FROM idsclienteslibres");       
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }        
}else{///no existen numeros de serviports eliminados
    $query = mysqli_query($mysqli,"SELECT max(id_cliente)+1 as 'numero' FROM cliente");    
    $result_register = mysqli_fetch_array($query);
    $total_registro = $result_register['numero'];
    if($total_registro == null){
       echo '[{"numero":"0"}]';
        ///$result = mysqli_fetch_array($query);
        //$numero = $result['numero']+1;       
        //$miArray = array("respuesta"=>$numero);
        //echo json_encode($miArray);
    }else{///no existen clientes por ende es 0        
        $query = mysqli_query($mysqli,"SELECT max(id_cliente)+1 as 'numero' FROM cliente");    
         while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
            echo json_encode($data);	
        }        
    }
}
mysqli_close($mysqli);
?>