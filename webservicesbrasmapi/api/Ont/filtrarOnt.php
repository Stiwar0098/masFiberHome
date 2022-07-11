<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];
if($filtrar=="" or $filtrar == null){
    $query = mysqli_query($mysqli,"SELECT o.id_ont, o.serie_ont,o.id_modelo,m.nombre_modelosont,o.responsable_ont,o.numeroont,o.estado_ont FROM ont o INNER JOIN modelosont m on m.id_modelosont=o.id_modelo WHERE (o.serie_ont like '%' or m.nombre_modelosont like '%' or o.responsable_ont like '%') and o.estado_ont='activo'");
}else{
    $query = mysqli_query($mysqli,"SELECT o.id_ont, o.serie_ont,o.id_modelo,m.nombre_modelosont,o.responsable_ont,o.numeroont,o.estado_ont FROM ont o INNER JOIN modelosont m on m.id_modelosont=o.id_modelo WHERE (o.serie_ont LIKE '$filtrar%' or m.nombre_modelosont like '$filtrar%' or o.responsable_ont like '$filtrar%')and o.estado_ont='activo'"); 
}
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>