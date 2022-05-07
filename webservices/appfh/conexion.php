<?php
    $host = 'localhost';
    $user = 'uafw5zcgshbii';
    $password = 'odv9innggkqh';
    $db = 'dbsplgajsg2l05';
    $mysqli = @mysqli_connect($host,$user,$password,$db);
    if(!$mysqli){
        echo "Error en la conexion44";        
    }else{
        echo "exitosa";
    }    
?>