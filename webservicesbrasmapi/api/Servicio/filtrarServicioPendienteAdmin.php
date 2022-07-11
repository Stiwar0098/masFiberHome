<?php
include "../conexion.php"; 
$query = mysqli_query($mysqli,"SELECT c.id_cliente,c.id_clientepersona,CONCAT(cp.nombre_clientepersona,' ',cp.apellido_clientepersona) as 'nombre_clientepersona',c.id_cajanivel2,c2.nombre_cajanivel2,c.id_ont,o.serie_ont,o.numeroont,c.usuario_cliente,c.id_planes, p.nombre_planes,c.direccion_cliente,c.referencia_cliente,c.fecha_instalacion_cliente,c.hilocajanivel2_cliente,c.direccionip_cliente,c.ip_cliente,c.latitud_cliente,c.longitud_cliente,c.comandoplanes_cliente,c.interfazponcard_cliente,c.agregaront_cliente,c.equipobridge_cliente,c.quit,c.eliminarservicio_cliente,c.agregarserviciopuerto_cliente,c.agregarplancliente_cliente,c.agregardescripcionpuerto_cliente,c.eliminaront_cliente,c.id_usuario,u.nombre_usuario,c.estado_cliente FROM cliente c INNER JOIN clientepersona cp on c.id_clientepersona=cp.id_clientepersona INNER JOIN cajanivel2 c2 on c.id_cajanivel2=c2.id_cajanivel2 INNER JOIN ont o on o.id_ont=c.id_ont INNER JOIN planes p on p.id_planes=c.id_planes INNER JOIN usuario u on u.id_usuario=c.id_usuario WHERE c.estado_cliente='pendiente'"); 
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>