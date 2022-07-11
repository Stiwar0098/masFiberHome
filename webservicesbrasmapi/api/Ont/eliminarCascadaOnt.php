<?php
header('Content-Type: application/json');
include "../conexion.php";     
    //$query_insert = mysqli_query($mysqli, "DELETE c from provincia p INNER JOIN ciudad c on c.id_provincia=p.id_provincia where p.id_provincia='$_GET[id]'");        
      //  $query_insert = mysqli_query($mysqli, "DELETE from provincia where id_pais='$_GET[id]'");        
            $query_insert = mysqli_query($mysqli, "DELETE from ont where id_ont ='$_GET[id]'");
            $can=mysqli_affected_rows($mysqli);
            if($can==1){
                echo '{"respuesta":"ok"}';
            }else{
                echo '{"respuesta":"error en la eliminacion en cascada"}';
            }                        

mysqli_close($mysqli);
?>