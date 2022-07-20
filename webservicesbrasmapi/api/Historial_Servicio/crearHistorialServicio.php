<?php
header('Content-Type: application/json');
include "../conexion.php";
$query = mysqli_query($mysqli, "SELECT * FROM ont where serie_ont='$_GET[serie_ont]'");
$result = mysqli_num_rows($query);
    $result_register = mysqli_fetch_array($query);
    $idont = $result_register['id_ont'];    
    $serie = $result_register['serie_ont'];
    $numero = $result_register['numeroont'];
    $responsable = $result_register['responsable_ont'];
        $query_insert = mysqli_query($mysqli, "INSERT INTO Historial_Cliente 
                                                VALUES (0,'$_GET[id_cliente]',
                                                '$_GET[usuario_cliente]',
                                                '$_GET[direccion_cliente]',
                                                '$_GET[referencia_cliente]',
                                                '$_GET[fecha_instalacion_cliente]',
                                                '$_GET[longitud_cliente]',
                                                '$_GET[latitud_cliente]',
                                                '$_GET[id_planes]',
                                                '$idont',
                                                '$serie',
                                                '$numero',
                                                '$responsable',
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
                                                '$_GET[date2]',
                                                '$_GET[estado_cliente]')");
                if ($query_insert) {    
                    echo '{"respuesta":"ok"}';                
                } else {
                    echo '{"respuesta":"error en la creacion crearHistorialServicio"}';
                } 
mysqli_close($mysqli);
