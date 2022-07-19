<?php
header('Content-Type: application/json');
include "../conexion.php";       
    $query_insert = mysqli_query($mysqli, "DELETE from Historial_Cliente");
    $can=mysqli_affected_rows($mysqli);
    if($can>0){        
        echo '{"respuesta":"ok"}';                    
    }else{
        echo '{"respuesta":"error en la eliminacion eliminarHistorialServicio"}';
    }   
mysqli_close($mysqli);
?>