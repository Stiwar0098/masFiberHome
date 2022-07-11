<?php
header('Content-Type: application/json');
include "../conexion.php";     
    $query_insert = mysqli_query($mysqli, "UPDATE cliente set usuario_cliente='$_GET[usuario_cliente]',direccion_cliente='$_GET[direccion_cliente]',referencia_cliente='$_GET[referencia_cliente]',fecha_instalacion_cliente='$_GET[fecha_instalacion_cliente]',longitud_cliente='$_GET[longitud_cliente]',latitud_cliente='$_GET[latitud_cliente]',id_planes='$_GET[id_planes]',id_ont='$_GET[id_ont]',id_cajanivel2='$_GET[id_cajanivel2]',id_clientepersona='$_GET[id_clientepersona]',hilocajanivel2_cliente='$_GET[hilocajanivel2_cliente]',direccionip_cliente='$_GET[direccionip_cliente]',comandoplanes_cliente='$_GET[comandoplanes_cliente]',interfazponcard_cliente='$_GET[interfazponcard_cliente]',agregaront_cliente='$_GET[agregaront_cliente]',equipobridge_cliente='$_GET[equipobridge_cliente]',quit='$_GET[quit]',eliminarservicio_cliente='$_GET[eliminarservicio_cliente]',agregarserviciopuerto_cliente='$_GET[agregarserviciopuerto_cliente]',agregarplancliente_cliente='$_GET[agregarplancliente_cliente]',agregardescripcionpuerto_cliente='$_GET[agregardescripcionpuerto_cliente]',eliminaront_cliente='$_GET[eliminaront_cliente]',id_usuario='$_GET[id_usuario]',estado_cliente='$_GET[estado_cliente]' where id_cliente='$_GET[id_cliente]'");
    $can=mysqli_affected_rows($mysqli);
    if($can==1){
        echo '{"respuesta":"ok"}';
    }else{
        echo '{"respuesta":"error en la edicion editarservicio"}';
    }
mysqli_close($mysqli);
?>