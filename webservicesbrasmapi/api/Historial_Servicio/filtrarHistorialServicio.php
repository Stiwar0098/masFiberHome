<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT c.id,c.id_cliente,c.id_clientepersona,CONCAT(cp.nombre_clientepersona,' ',cp.apellido_clientepersona) as 'nombre_clientepersona',c.id_cajanivel2,c2.nombre_cajanivel2,c.id_ont,c.serie_ont,c.numeroont,c.responsable_ont,c.usuario_cliente,c.id_planes, p.nombre_planes,c.direccion_cliente,c.referencia_cliente,c.fecha_instalacion_cliente,c.hilocajanivel2_cliente,c.direccionip_cliente,c.ip_cliente,c.latitud_cliente,c.longitud_cliente,c.comandoplanes_cliente,c.interfazponcard_cliente,c.agregaront_cliente,c.equipobridge_cliente,c.quit,c.eliminarservicio_cliente,c.agregarserviciopuerto_cliente,c.agregarplancliente_cliente,c.agregardescripcionpuerto_cliente,c.eliminaront_cliente,c.id_usuario,u.nombre_usuario,c.opcion_cliente,c.comando_copiar_cliente,c.date2,c.estado_cliente FROM Historial_Cliente c INNER JOIN clientepersona cp on c.id_clientepersona=cp.id_clientepersona INNER JOIN cajanivel2 c2 on c.id_cajanivel2=c2.id_cajanivel2 INNER JOIN planes p on p.id_planes=c.id_planes INNER JOIN usuario u on u.id_usuario=c.id_usuario WHERE (c.id_cliente like '%' or cp.nombre_clientepersona like '%' or c2.nombre_cajanivel2 like '%' or u.nombre_usuario like '%' or c.direccion_cliente like '%' or c.referencia_cliente like '%') ORDER by c.id DESC");
}else{
    $query = mysqli_query($mysqli,"SELECT c.id,c.id_cliente,c.id_clientepersona,CONCAT(cp.nombre_clientepersona,' ',cp.apellido_clientepersona) as 'nombre_clientepersona',c.id_cajanivel2,c2.nombre_cajanivel2,c.id_ont,c.serie_ont,c.numeroont,c.responsable_ont,c.usuario_cliente,c.id_planes, p.nombre_planes,c.direccion_cliente,c.referencia_cliente,c.fecha_instalacion_cliente,c.hilocajanivel2_cliente,c.direccionip_cliente,c.ip_cliente,c.latitud_cliente,c.longitud_cliente,c.comandoplanes_cliente,c.interfazponcard_cliente,c.agregaront_cliente,c.equipobridge_cliente,c.quit,c.eliminarservicio_cliente,c.agregarserviciopuerto_cliente,c.agregarplancliente_cliente,c.agregardescripcionpuerto_cliente,c.eliminaront_cliente,c.id_usuario,u.nombre_usuario,c.opcion_cliente,c.comando_copiar_cliente,c.date2,c.estado_cliente FROM Historial_Cliente c INNER JOIN clientepersona cp on c.id_clientepersona=cp.id_clientepersona INNER JOIN cajanivel2 c2 on c.id_cajanivel2=c2.id_cajanivel2 INNER JOIN planes p on p.id_planes=c.id_planes INNER JOIN usuario u on u.id_usuario=c.id_usuario WHERE (c.id_cliente like '$filtrar%' or cp.nombre_clientepersona like '%$filtrar%' or c2.nombre_cajanivel2 like '$filtrar%' or u.nombre_usuario like '$filtrar%' or c.direccion_cliente like '$filtrar%' or c.referencia_cliente like '$filtrar%') ORDER by c.id DESC"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>