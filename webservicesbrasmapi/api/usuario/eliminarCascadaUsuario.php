<?php
header('Content-Type: application/json');
include "../conexion.php";     
    //$query_insert = mysqli_query($mysqli, "DELETE c from provincia p INNER JOIN ciudad c on c.id_provincia=p.id_provincia INNER JOIN pais pa on pa.id_pais=p.id_pais where pa.id_pais='$_GET[id]'");        
      //  $query_insert = mysqli_query($mysqli, "DELETE from provincia where id_pais='$_GET[id]'");        
            $query_insert = mysqli_query($mysqli, "DELETE from usuario where id_usuario='$_GET[id]'");
            $can=mysqli_affected_rows($mysqli);
            if($can==1){
                echo '{"respuesta":"ok"}';
            }else{
                echo '{"respuesta":"error en la eliminacion PAIS"}';
            }                        

mysqli_close($mysqli);
?>