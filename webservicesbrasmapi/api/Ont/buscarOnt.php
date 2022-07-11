<?php
include "../conexion.php"; 
$filtrar=$_GET['filtrar'];    
    $query = mysqli_query($mysqli,"SELECT o.id_ont, o.serie_ont,o.id_modelo,m.nombre_modelosont,o.responsable_ont,o.numeroont,o.estado_ont FROM ont o INNER JOIN modelosont m on m.id_modelosont=o.id_modelo where (o.id_ont='$filtrar' or o.serie_ont='$filtrar' or m.nombre_modelosont='$filtrar' or o.responsable_ont='$filtrar') and o.estado_ont='activo'");
mysqli_close($mysqli);
$result = mysqli_num_rows($query);
if($result > 0){
    while ($data = mysqli_fetch_all($query,MYSQLI_ASSOC)){
        echo json_encode($data);	
    }
}
?>