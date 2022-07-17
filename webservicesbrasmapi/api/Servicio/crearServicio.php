<?php
header('Content-Type: application/json');
include "../conexion.php";
$query = mysqli_query($mysqli, "SELECT id_ont FROM ont where serie_ont='$_GET[serie_ont]'");
$result = mysqli_num_rows($query);
if ($result > 0) {
    $result_register = mysqli_fetch_array($query);
    $idont = $result_register['id_ont'];
    $query = mysqli_query($mysqli, "SELECT * FROM cliente where id_cliente='$_GET[id_cliente]'");
    $result = mysqli_num_rows($query);
    if ($result < 1) {        
        $query_insert = mysqli_query($mysqli, "INSERT INTO cliente 
                                                VALUES ('$_GET[id_cliente]',
                                                '$_GET[usuario_cliente]',
                                                '$_GET[direccion_cliente]',
                                                '$_GET[referencia_cliente]',
                                                '$_GET[fecha_instalacion_cliente]',
                                                '$_GET[longitud_cliente]',
                                                '$_GET[latitud_cliente]',
                                                '$_GET[id_planes]',
                                                '$idont',
                                                '$_GET[id_cajanivel2]',
                                                '$_GET[id_clientepersona]',
                                                '$_GET[hilocajanivel2_cliente]',
                                                '$_GET[direccionip_cliente]',
                                                '$_GET[ip_cliente]',
                                                '$_GET[comandoplanes_cliente]',
                                                '$_GET[interfazponcard_cliente]',
                                                '$_GET[agregaront_cliente]',
                                                '$_GET[equipobridge_cliente]',
                                                '$_GET[quit]',
                                                '$_GET[eliminarservicio_cliente]',
                                                '$_GET[agregarserviciopuerto_cliente]',
                                                '$_GET[agregarplancliente_cliente]',
                                                '$_GET[agregardescripcionpuerto_cliente]',
                                                '$_GET[eliminaront_cliente]',
                                                '$_GET[id_usuario]',
                                                '$_GET[opcion_cliente]',
                                                '$_GET[comando_copiar_cliente]',
                                                'pendiente')");
        if ($query_insert) {            
            $query = mysqli_query($mysqli, "SELECT id_idsclienteslibres FROM idsclienteslibres where numero_idsclienteslibres='$_GET[id_cliente]'");
            $result = mysqli_num_rows($query);
            if ($result > 0) {
                $result_register = mysqli_fetch_array($query);
                $total_registro = $result_register['id_idsclienteslibres'];
                $query = mysqli_query($mysqli, "DELETE from idsclienteslibres where id_idsclienteslibres='$total_registro'");
                if ($query_insert) {    
                    echo '{"respuesta":"ok"}';                
                } else {
                    echo '{"respuesta":"error en la eliminacion crearServicio"}';
                }
            }else{
                echo '{"respuesta":"ok"}'; 
            }
        } else {
            echo '{"respuesta":"error en la insercion crearServicio"}';
        }
    } else {
        echo '{"respuesta":"el serviport ya existe"}';
    }
}else{
    echo '{"respuesta":"error ont crearservicio"}';
}

mysqli_close($mysqli);
