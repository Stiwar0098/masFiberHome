-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 11-07-2022 a las 05:51:22
-- Versión del servidor: 5.7.32-35-log
-- Versión de PHP: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbsplgajsg2l05`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `crearIpsDeLaVlan` (IN `bucle` INT(11) UNSIGNED, IN `ipInicio` INT(11) UNSIGNED, IN `nombre` VARCHAR(50))  BEGIN
	DECLARE id_vlan2 INT;          
    DECLARE i INT;    	
    set i=1;
    set id_vlan2=(SELECT id_vlan FROM vlan where nombre_vlan=nombre);
    WHILE i<=bucle DO
    INSERT INTO rangodireccionesip VALUES (0,id_vlan2,null,ipInicio,"desactivo");
    SET ipInicio=ipInicio+1;
    set i=i+1;
    END WHILE;
    set i=0;
    WHILE i<=127 DO
    INSERT INTO rangoont VALUES (0,id_vlan2,null,i,"desactivo");    
    set i=i+1;
    END WHILE;
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `crearRangoHilosCaja1` (IN `hilos` INT(11) UNSIGNED, IN `nombre` VARCHAR(50))  BEGIN
    DECLARE i INT;    	
    DECLARE id_cajanivel12 INT;    	
    set i=1;
    set id_cajanivel12=(SELECT id_cajanivel1 FROM cajanivel1 where nombre_cajanivel1=nombre);
      WHILE i<=hilos DO
    INSERT INTO rangohiloscaja1 VALUES (0,id_cajanivel12,null,i,"desactivo");    
    set i=i+1;
    END WHILE;
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `crearRangoHilosCaja2` (IN `hilos` INT(11), IN `nombre` VARCHAR(50))  BEGIN
    DECLARE i INT;    	
    DECLARE id_cajanivel12 INT;    	
    set i=1;
    set id_cajanivel12=(SELECT id_cajanivel2 FROM cajanivel2 where nombre_cajanivel2=nombre);
      WHILE i<=hilos DO
    INSERT INTO rangohiloscaja2 VALUES (0,id_cajanivel12,null,i,"desactivo");    
    set i=i+1;
    END WHILE;
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `obtenerDireccionIpAuto` (IN `id_vlan2` INT)  BEGIN
SELECT `id_rangodireccionesip`,min(`ip_rangodireccionesip`) as "ip_rangodireccionesip" FROM `rangodireccionesip` WHERE `id_vlan`=id_vlan2 and `estado_rangodireccionesip`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `obtenerNumeroHiloCaja1Auto` (IN `id_cajanivel12` INT(11))  BEGIN
SELECT `id_rangohiloscaja1`,min(`numero_rangohiloscaja1`) as "numero_rangohiloscaja1" FROM `rangohiloscaja1` WHERE `id_cajanivel1`=id_cajanivel12 and `estado_rangohiloscaja1`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `obtenerNumeroHiloCaja2Auto` (IN `id_cajanivel22` INT(11))  BEGIN
SELECT `id_rangohiloscaja2`,min(`numero_rangohiloscaja2`) as "numero_rangohiloscaja2" FROM `rangohiloscaja2` WHERE `id_cajanivel2`=id_cajanivel22 and `estado_rangohiloscaja2`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `obtenerNumeroOntAuto` (IN `id_vlan2` INT)  BEGIN
SELECT `id_rangoont`,min(`numero_rangoont`) as "numero_rangoont" FROM `rangoont` WHERE `id_vlan`=id_vlan2 and `estado_rangoont`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `validarDireccionIpManual` (IN `id_vlan2` INT, IN `ip_rangodireccionesip2` INT)  BEGIN
SELECT * FROM `rangodireccionesip` WHERE `id_vlan`=id_vlan2 and `ip_rangodireccionesip`=ip_rangodireccionesip2 and `estado_rangodireccionesip`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `validarNumeroHiloCaja1Manual` (IN `id_cajanivel12` INT(11), IN `numero_rangohiloscaja12` INT(11))  BEGIN
SELECT * FROM `rangohiloscaja1` WHERE `id_cajanivel1`=id_cajanivel12 and `numero_rangohiloscaja1`=numero_rangohiloscaja12 and `estado_rangohiloscaja1`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `validarNumeroHiloCaja2Manual` (IN `id_cajanivel22` INT(11), IN `numero_rangohiloscaja22` INT(11))  BEGIN
SELECT * FROM `rangohiloscaja2` WHERE `id_cajanivel2`=id_cajanivel22 and `numero_rangohiloscaja2`=numero_rangohiloscaja22 and `estado_rangohiloscaja2`="desactivo";
END$$

CREATE DEFINER=`sabacvcxx9qh6`@`localhost` PROCEDURE `validarNumeroOntManual` (IN `id_vlan2` INT, IN `numero_rangoont2` INT)  BEGIN
SELECT * FROM `rangoont` WHERE `id_vlan`=id_vlan2 and `numero_rangoont`=numero_rangoont2 and `estado_rangoont`="desactivo";
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajanivel1`
--

CREATE TABLE `cajanivel1` (
  `id_cajanivel1` int(11) NOT NULL,
  `nombre_cajanivel1` varchar(50) NOT NULL,
  `abreviatura_cajanivel1` varchar(100) NOT NULL,
  `direccion_cajanivel1` varchar(250) NOT NULL,
  `referencia_cajanivel1` varchar(250) DEFAULT NULL,
  `latitud_cajanivel1` varchar(50) DEFAULT NULL,
  `longitud_cajanivel1` varchar(50) DEFAULT NULL,
  `id_vlan` int(11) NOT NULL,
  `id_ciudad` int(11) NOT NULL,
  `cantidadhilos_cajanivel1` int(11) NOT NULL,
  `estado_cajanivel1` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cajanivel1`
--

INSERT INTO `cajanivel1` (`id_cajanivel1`, `nombre_cajanivel1`, `abreviatura_cajanivel1`, `direccion_cajanivel1`, `referencia_cajanivel1`, `latitud_cajanivel1`, `longitud_cajanivel1`, `id_vlan`, `id_ciudad`, `cantidadhilos_cajanivel1`, `estado_cajanivel1`) VALUES
(10, 'caja aki', 'caki12', 'Jja', 'haha', '-3.4743082', '-80.2386179', 10, 2, 8, 'activo'),
(11, 'cajavlan2', 'cja2', 'bshs', 'hdhd', '-3.4743428', '-80.2386539', 11, 2, 8, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cajanivel2`
--

CREATE TABLE `cajanivel2` (
  `id_cajanivel2` int(11) NOT NULL,
  `nombre_cajanivel2` varchar(50) NOT NULL,
  `abreviatura_cajanivel2` varchar(150) NOT NULL,
  `direccion_cajanivel2` varchar(250) NOT NULL,
  `referencia` varchar(250) NOT NULL,
  `latitud_cajanivel2` varchar(50) NOT NULL,
  `longitud_cajanivel2` varchar(50) NOT NULL,
  `id_cajanivel1` int(11) NOT NULL,
  `hilocajanivel1_cajanivel2` int(11) NOT NULL,
  `cantidadhilos_cajanivel2` int(11) NOT NULL,
  `estado_cajanivel2` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cajanivel2`
--

INSERT INTO `cajanivel2` (`id_cajanivel2`, `nombre_cajanivel2`, `abreviatura_cajanivel2`, `direccion_cajanivel2`, `referencia`, `latitud_cajanivel2`, `longitud_cajanivel2`, `id_cajanivel1`, `hilocajanivel1_cajanivel2`, `cantidadhilos_cajanivel2`, `estado_cajanivel2`) VALUES
(25, 'caja habana', 'chab12', 'zbz', 'vsvz', '-3.4743363', '-80.2386259', 10, 1, 8, 'activo'),
(26, 'caja mercado', 'cmercd', 'vzvz', 'vzvz', '-3.4743049', '-80.2386211', 10, 2, 8, 'activo'),
(27, 'cajavlan22', 'bsbs', 'bdbd', 'bbb', '-3.4743171', '-80.2386239', 11, 1, 8, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

CREATE TABLE `ciudad` (
  `id_ciudad` int(11) NOT NULL,
  `nombre_ciudad` varchar(50) NOT NULL,
  `id_provincia` int(11) NOT NULL,
  `estado_ciudad` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`id_ciudad`, `nombre_ciudad`, `id_provincia`, `estado_ciudad`) VALUES
(2, 'Huaquillas', 5, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `usuario_cliente` varchar(150) DEFAULT NULL,
  `direccion_cliente` varchar(250) DEFAULT NULL,
  `referencia_cliente` varchar(250) DEFAULT NULL,
  `fecha_instalacion_cliente` varchar(50) DEFAULT NULL,
  `longitud_cliente` varchar(50) DEFAULT NULL,
  `latitud_cliente` varchar(50) DEFAULT NULL,
  `id_planes` int(11) DEFAULT NULL,
  `id_ont` int(11) DEFAULT NULL,
  `id_cajanivel2` int(11) DEFAULT NULL,
  `id_clientepersona` int(11) DEFAULT NULL,
  `hilocajanivel2_cliente` int(11) DEFAULT NULL,
  `direccionip_cliente` varchar(50) DEFAULT NULL,
  `ip_cliente` int(11) DEFAULT NULL,
  `comandoplanes_cliente` varchar(250) DEFAULT NULL,
  `interfazponcard_cliente` varchar(250) DEFAULT NULL,
  `agregaront_cliente` varchar(250) DEFAULT NULL,
  `equipobridge_cliente` varchar(250) DEFAULT NULL,
  `quit` varchar(250) DEFAULT NULL,
  `eliminarservicio_cliente` varchar(250) DEFAULT NULL,
  `agregarserviciopuerto_cliente` varchar(250) DEFAULT NULL,
  `agregarplancliente_cliente` varchar(250) DEFAULT NULL,
  `agregardescripcionpuerto_cliente` varchar(250) DEFAULT NULL,
  `eliminaront_cliente` varchar(250) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `estado_cliente` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `usuario_cliente`, `direccion_cliente`, `referencia_cliente`, `fecha_instalacion_cliente`, `longitud_cliente`, `latitud_cliente`, `id_planes`, `id_ont`, `id_cajanivel2`, `id_clientepersona`, `hilocajanivel2_cliente`, `direccionip_cliente`, `ip_cliente`, `comandoplanes_cliente`, `interfazponcard_cliente`, `agregaront_cliente`, `equipobridge_cliente`, `quit`, `eliminarservicio_cliente`, `agregarserviciopuerto_cliente`, `agregarplancliente_cliente`, `agregardescripcionpuerto_cliente`, `eliminaront_cliente`, `id_usuario`, `estado_cliente`) VALUES
(0, 'bmacas_caki12_chab12', 'hxhx', 'yftd', '10-7-2022', '-80.2386875', '-3.4741701', 2, 79, 25, 1, 1, '10.170.5.2', 2, 'service-port 0 inbound traffic-table index 60 outbound traffic-table index 60', 'interface gpon 0/1', '', 'quit', 'undo service-port 0', 'service-port 0 vlan 1000 gpon 1/1/2 ont 0 gemport 100 multi-service user-vlan 100 tag-transform translate', 'service-port 0 inbound traffic-table index 60 outbound traffic-table index 60', 'service-port 0 inbound traffic-table index 60 outbound traffic-table index 60', 'service-port desc 0 description \"bmacas_caki12_chab12-10.170.5.2\"', 'ont delete 2 0', 1, 'activo'),
(1, 'mpiedra_cja2_bsbs', 'gdvd', 'gdhd', '10-7-2022', '-80.2386702', '-3.4742613', 2, 80, 27, 2, 1, '10.170.10.5', 3, 'service-port 1 inbound traffic-table index 60 outbound traffic-table index 60', 'interface gpon 0/2', 'ont add 3 0 sn-auth \"hdhd\" omci ont-lineprofile-id 5 ont-srvprofile-id 5 desc \"mpiedra_cja2_bsbs\"', 'ont port native-vlan 3 0 eth 1 vlan 100 priority 0', 'quit', 'undo service-port 1', 'service-port 1 vlan 2000 gpon 2/2/3 ont 0 gemport 100 multi-service user-vlan 100 tag-transform translate', 'service-port 1 inbound traffic-table index 60 outbound traffic-table index 60', 'service-port desc 1 description \"mpiedra_cja2_bsbs-10.170.10.5\"', 'ont delete 3 0', 1, 'pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientepersona`
--

CREATE TABLE `clientepersona` (
  `id_clientepersona` int(11) NOT NULL,
  `cedula_clientepersona` varchar(50) NOT NULL,
  `nombre_clientepersona` varchar(50) NOT NULL,
  `apellido_clientepersona` varchar(50) NOT NULL,
  `correo_clientepersona` varchar(100) DEFAULT NULL,
  `telefono1_clientepersona` varchar(50) DEFAULT NULL,
  `telefono2_clientepersona` varchar(50) DEFAULT NULL,
  `estado_clientepersona` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `clientepersona`
--

INSERT INTO `clientepersona` (`id_clientepersona`, `cedula_clientepersona`, `nombre_clientepersona`, `apellido_clientepersona`, `correo_clientepersona`, `telefono1_clientepersona`, `telefono2_clientepersona`, `estado_clientepersona`) VALUES
(1, '1111', 'brayan', 'macas', '1111', '1111', '1111', 'activo'),
(2, '0705869456', 'mercy lucia', 'piedra loaiza', 'mercy@gmail.com', '0985764553', '0985764583', 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idsclienteslibres`
--

CREATE TABLE `idsclienteslibres` (
  `id_idsclienteslibres` int(11) NOT NULL,
  `numero_idsclienteslibres` int(11) NOT NULL,
  `estado_idsclienteslibres` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modelosont`
--

CREATE TABLE `modelosont` (
  `id_modelosont` int(11) NOT NULL,
  `nombre_modelosont` varchar(100) NOT NULL,
  `tipo_modelosont` varchar(100) NOT NULL,
  `estado_modelosont` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `modelosont`
--

INSERT INTO `modelosont` (`id_modelosont`, `nombre_modelosont`, `tipo_modelosont`, `estado_modelosont`) VALUES
(8, 'h283kdn', 'NORMAL', 'activo'),
(9, 'hy564', 'DOBLE BANDA', 'activo'),
(10, 'jgt54', 'BRIDGE', 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ont`
--

CREATE TABLE `ont` (
  `id_ont` int(11) NOT NULL,
  `serie_ont` varchar(50) NOT NULL,
  `id_modelo` int(11) NOT NULL,
  `responsable_ont` varchar(50) NOT NULL,
  `numeroont` int(11) DEFAULT NULL,
  `estado_ont` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ont`
--

INSERT INTO `ont` (`id_ont`, `serie_ont`, `id_modelo`, `responsable_ont`, `numeroont`, `estado_ont`) VALUES
(79, 'gff', 9, 'EMPRESA', 0, 'activo'),
(80, 'hdhd', 10, 'EMPRESA', 1, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id_pais` int(11) NOT NULL,
  `nombre_pais` varchar(50) NOT NULL,
  `estado_pais` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`id_pais`, `nombre_pais`, `estado_pais`) VALUES
(12, 'Ecuador', 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `planes`
--

CREATE TABLE `planes` (
  `id_planes` int(11) NOT NULL,
  `nombre_planes` int(11) NOT NULL,
  `estado_planes` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `planes`
--

INSERT INTO `planes` (`id_planes`, `nombre_planes`, `estado_planes`) VALUES
(2, 60, 'activo'),
(3, 70, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `provincia`
--

CREATE TABLE `provincia` (
  `id_provincia` int(11) NOT NULL,
  `nombre_provincia` varchar(50) NOT NULL,
  `id_pais` int(11) NOT NULL,
  `estado_provincia` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `provincia`
--

INSERT INTO `provincia` (`id_provincia`, `nombre_provincia`, `id_pais`, `estado_provincia`) VALUES
(5, 'El Oro', 12, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rangodireccionesip`
--

CREATE TABLE `rangodireccionesip` (
  `id_rangodireccionesip` int(11) NOT NULL,
  `id_vlan` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `ip_rangodireccionesip` int(11) NOT NULL,
  `estado_rangodireccionesip` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rangodireccionesip`
--

INSERT INTO `rangodireccionesip` (`id_rangodireccionesip`, `id_vlan`, `id_cliente`, `ip_rangodireccionesip`, `estado_rangodireccionesip`) VALUES
(22, 10, 0, 2, 'activo'),
(23, 10, NULL, 3, 'desactivo'),
(24, 10, 0, 4, 'desactivo'),
(25, 10, NULL, 5, 'desactivo'),
(26, 10, 0, 6, 'desactivo'),
(27, 10, 0, 7, 'desactivo'),
(28, 10, NULL, 8, 'desactivo'),
(29, 10, 0, 9, 'desactivo'),
(30, 10, 0, 10, 'desactivo'),
(31, 11, 1, 5, 'activo'),
(32, 11, 0, 6, 'desactivo'),
(33, 11, 0, 7, 'desactivo'),
(34, 11, 0, 8, 'desactivo'),
(35, 11, 0, 9, 'desactivo'),
(36, 12, NULL, 6, 'desactivo'),
(37, 12, NULL, 7, 'desactivo'),
(38, 12, NULL, 8, 'desactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rangohiloscaja1`
--

CREATE TABLE `rangohiloscaja1` (
  `id_rangohiloscaja1` int(11) NOT NULL,
  `id_cajanivel1` int(11) NOT NULL,
  `id_cajanivel2` int(11) DEFAULT NULL,
  `numero_rangohiloscaja1` int(11) NOT NULL,
  `estado_rangohiloscaja1` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rangohiloscaja1`
--

INSERT INTO `rangohiloscaja1` (`id_rangohiloscaja1`, `id_cajanivel1`, `id_cajanivel2`, `numero_rangohiloscaja1`, `estado_rangohiloscaja1`) VALUES
(33, 10, 25, 1, 'activo'),
(34, 10, 26, 2, 'activo'),
(35, 10, NULL, 3, 'desactivo'),
(36, 10, NULL, 4, 'desactivo'),
(37, 10, NULL, 5, 'desactivo'),
(38, 10, NULL, 6, 'desactivo'),
(39, 10, NULL, 7, 'desactivo'),
(40, 10, NULL, 8, 'desactivo'),
(41, 11, 27, 1, 'activo'),
(42, 11, NULL, 2, 'desactivo'),
(43, 11, NULL, 3, 'desactivo'),
(44, 11, NULL, 4, 'desactivo'),
(45, 11, NULL, 5, 'desactivo'),
(46, 11, NULL, 6, 'desactivo'),
(47, 11, NULL, 7, 'desactivo'),
(48, 11, NULL, 8, 'desactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rangohiloscaja2`
--

CREATE TABLE `rangohiloscaja2` (
  `id_rangohiloscaja2` int(11) NOT NULL,
  `id_cajanivel2` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `numero_rangohiloscaja2` int(11) NOT NULL,
  `estado_rangohiloscaja2` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rangohiloscaja2`
--

INSERT INTO `rangohiloscaja2` (`id_rangohiloscaja2`, `id_cajanivel2`, `id_cliente`, `numero_rangohiloscaja2`, `estado_rangohiloscaja2`) VALUES
(77, 25, 0, 1, 'activo'),
(78, 25, NULL, 2, 'desactivo'),
(79, 25, NULL, 3, 'desactivo'),
(80, 25, NULL, 4, 'desactivo'),
(81, 25, NULL, 5, 'desactivo'),
(82, 25, NULL, 6, 'desactivo'),
(83, 25, NULL, 7, 'desactivo'),
(84, 25, NULL, 8, 'desactivo'),
(85, 26, NULL, 1, 'desactivo'),
(86, 26, NULL, 2, 'desactivo'),
(87, 26, NULL, 3, 'desactivo'),
(88, 26, NULL, 4, 'desactivo'),
(89, 26, NULL, 5, 'desactivo'),
(90, 26, NULL, 6, 'desactivo'),
(91, 26, NULL, 7, 'desactivo'),
(92, 26, NULL, 8, 'desactivo'),
(93, 27, 1, 1, 'activo'),
(94, 27, NULL, 2, 'desactivo'),
(95, 27, NULL, 3, 'desactivo'),
(96, 27, NULL, 4, 'desactivo'),
(97, 27, NULL, 5, 'desactivo'),
(98, 27, NULL, 6, 'desactivo'),
(99, 27, NULL, 7, 'desactivo'),
(100, 27, NULL, 8, 'desactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rangoont`
--

CREATE TABLE `rangoont` (
  `id_rangoont` int(11) NOT NULL,
  `id_vlan` int(11) NOT NULL,
  `id_ont` int(11) DEFAULT NULL,
  `numero_rangoont` int(11) NOT NULL,
  `estado_rangoont` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `rangoont`
--

INSERT INTO `rangoont` (`id_rangoont`, `id_vlan`, `id_ont`, `numero_rangoont`, `estado_rangoont`) VALUES
(513, 10, 79, 0, 'activo'),
(514, 10, NULL, 1, 'desactivo'),
(515, 10, 0, 2, 'desactivo'),
(516, 10, 0, 3, 'desactivo'),
(517, 10, 0, 4, 'desactivo'),
(518, 10, 0, 5, 'desactivo'),
(519, 10, 0, 6, 'desactivo'),
(520, 10, 0, 7, 'desactivo'),
(521, 10, 0, 8, 'desactivo'),
(522, 10, 0, 9, 'desactivo'),
(523, 10, 0, 10, 'desactivo'),
(524, 10, 0, 11, 'desactivo'),
(525, 10, 0, 12, 'desactivo'),
(526, 10, 0, 13, 'desactivo'),
(527, 10, 0, 14, 'desactivo'),
(528, 10, 0, 15, 'desactivo'),
(529, 10, 0, 16, 'desactivo'),
(530, 10, 0, 17, 'desactivo'),
(531, 10, 0, 18, 'desactivo'),
(532, 10, 0, 19, 'desactivo'),
(533, 10, 0, 20, 'desactivo'),
(534, 10, 0, 21, 'desactivo'),
(535, 10, 0, 22, 'desactivo'),
(536, 10, 0, 23, 'desactivo'),
(537, 10, 0, 24, 'desactivo'),
(538, 10, 0, 25, 'desactivo'),
(539, 10, 0, 26, 'desactivo'),
(540, 10, 0, 27, 'desactivo'),
(541, 10, 0, 28, 'desactivo'),
(542, 10, 0, 29, 'desactivo'),
(543, 10, 0, 30, 'desactivo'),
(544, 10, 0, 31, 'desactivo'),
(545, 10, 0, 32, 'desactivo'),
(546, 10, 0, 33, 'desactivo'),
(547, 10, 0, 34, 'desactivo'),
(548, 10, 0, 35, 'desactivo'),
(549, 10, 0, 36, 'desactivo'),
(550, 10, 0, 37, 'desactivo'),
(551, 10, 0, 38, 'desactivo'),
(552, 10, 0, 39, 'desactivo'),
(553, 10, 0, 40, 'desactivo'),
(554, 10, 0, 41, 'desactivo'),
(555, 10, 0, 42, 'desactivo'),
(556, 10, 0, 43, 'desactivo'),
(557, 10, 0, 44, 'desactivo'),
(558, 10, 0, 45, 'desactivo'),
(559, 10, 0, 46, 'desactivo'),
(560, 10, 0, 47, 'desactivo'),
(561, 10, 0, 48, 'desactivo'),
(562, 10, 0, 49, 'desactivo'),
(563, 10, 0, 50, 'desactivo'),
(564, 10, 0, 51, 'desactivo'),
(565, 10, 0, 52, 'desactivo'),
(566, 10, 0, 53, 'desactivo'),
(567, 10, 0, 54, 'desactivo'),
(568, 10, 0, 55, 'desactivo'),
(569, 10, 0, 56, 'desactivo'),
(570, 10, 0, 57, 'desactivo'),
(571, 10, 0, 58, 'desactivo'),
(572, 10, 0, 59, 'desactivo'),
(573, 10, 0, 60, 'desactivo'),
(574, 10, 0, 61, 'desactivo'),
(575, 10, 0, 62, 'desactivo'),
(576, 10, 0, 63, 'desactivo'),
(577, 10, 0, 64, 'desactivo'),
(578, 10, 0, 65, 'desactivo'),
(579, 10, 0, 66, 'desactivo'),
(580, 10, 0, 67, 'desactivo'),
(581, 10, 0, 68, 'desactivo'),
(582, 10, 0, 69, 'desactivo'),
(583, 10, 0, 70, 'desactivo'),
(584, 10, 0, 71, 'desactivo'),
(585, 10, 0, 72, 'desactivo'),
(586, 10, 0, 73, 'desactivo'),
(587, 10, 0, 74, 'desactivo'),
(588, 10, 0, 75, 'desactivo'),
(589, 10, 0, 76, 'desactivo'),
(590, 10, 0, 77, 'desactivo'),
(591, 10, 0, 78, 'desactivo'),
(592, 10, 0, 79, 'desactivo'),
(593, 10, 0, 80, 'desactivo'),
(594, 10, 0, 81, 'desactivo'),
(595, 10, 0, 82, 'desactivo'),
(596, 10, 0, 83, 'desactivo'),
(597, 10, 0, 84, 'desactivo'),
(598, 10, 0, 85, 'desactivo'),
(599, 10, 0, 86, 'desactivo'),
(600, 10, 0, 87, 'desactivo'),
(601, 10, 0, 88, 'desactivo'),
(602, 10, 0, 89, 'desactivo'),
(603, 10, 0, 90, 'desactivo'),
(604, 10, 0, 91, 'desactivo'),
(605, 10, 0, 92, 'desactivo'),
(606, 10, 0, 93, 'desactivo'),
(607, 10, 0, 94, 'desactivo'),
(608, 10, 0, 95, 'desactivo'),
(609, 10, 0, 96, 'desactivo'),
(610, 10, 0, 97, 'desactivo'),
(611, 10, 0, 98, 'desactivo'),
(612, 10, 0, 99, 'desactivo'),
(613, 10, 0, 100, 'desactivo'),
(614, 10, 0, 101, 'desactivo'),
(615, 10, 0, 102, 'desactivo'),
(616, 10, 0, 103, 'desactivo'),
(617, 10, 0, 104, 'desactivo'),
(618, 10, 0, 105, 'desactivo'),
(619, 10, 0, 106, 'desactivo'),
(620, 10, 0, 107, 'desactivo'),
(621, 10, 0, 108, 'desactivo'),
(622, 10, 0, 109, 'desactivo'),
(623, 10, 0, 110, 'desactivo'),
(624, 10, 0, 111, 'desactivo'),
(625, 10, 0, 112, 'desactivo'),
(626, 10, 0, 113, 'desactivo'),
(627, 10, 0, 114, 'desactivo'),
(628, 10, 0, 115, 'desactivo'),
(629, 10, 0, 116, 'desactivo'),
(630, 10, 0, 117, 'desactivo'),
(631, 10, 0, 118, 'desactivo'),
(632, 10, 0, 119, 'desactivo'),
(633, 10, 0, 120, 'desactivo'),
(634, 10, 0, 121, 'desactivo'),
(635, 10, 0, 122, 'desactivo'),
(636, 10, 0, 123, 'desactivo'),
(637, 10, 0, 124, 'desactivo'),
(638, 10, 0, 125, 'desactivo'),
(639, 10, 0, 126, 'desactivo'),
(640, 10, 0, 127, 'desactivo'),
(641, 11, 80, 0, 'activo'),
(642, 11, 0, 1, 'desactivo'),
(643, 11, 0, 2, 'desactivo'),
(644, 11, 0, 3, 'desactivo'),
(645, 11, 0, 4, 'desactivo'),
(646, 11, 0, 5, 'desactivo'),
(647, 11, 0, 6, 'desactivo'),
(648, 11, 0, 7, 'desactivo'),
(649, 11, 0, 8, 'desactivo'),
(650, 11, 0, 9, 'desactivo'),
(651, 11, 0, 10, 'desactivo'),
(652, 11, 0, 11, 'desactivo'),
(653, 11, 0, 12, 'desactivo'),
(654, 11, 0, 13, 'desactivo'),
(655, 11, 0, 14, 'desactivo'),
(656, 11, 0, 15, 'desactivo'),
(657, 11, 0, 16, 'desactivo'),
(658, 11, 0, 17, 'desactivo'),
(659, 11, 0, 18, 'desactivo'),
(660, 11, 0, 19, 'desactivo'),
(661, 11, 0, 20, 'desactivo'),
(662, 11, 0, 21, 'desactivo'),
(663, 11, 0, 22, 'desactivo'),
(664, 11, 0, 23, 'desactivo'),
(665, 11, 0, 24, 'desactivo'),
(666, 11, 0, 25, 'desactivo'),
(667, 11, 0, 26, 'desactivo'),
(668, 11, 0, 27, 'desactivo'),
(669, 11, 0, 28, 'desactivo'),
(670, 11, 0, 29, 'desactivo'),
(671, 11, 0, 30, 'desactivo'),
(672, 11, 0, 31, 'desactivo'),
(673, 11, 0, 32, 'desactivo'),
(674, 11, 0, 33, 'desactivo'),
(675, 11, 0, 34, 'desactivo'),
(676, 11, 0, 35, 'desactivo'),
(677, 11, 0, 36, 'desactivo'),
(678, 11, 0, 37, 'desactivo'),
(679, 11, 0, 38, 'desactivo'),
(680, 11, 0, 39, 'desactivo'),
(681, 11, 0, 40, 'desactivo'),
(682, 11, 0, 41, 'desactivo'),
(683, 11, 0, 42, 'desactivo'),
(684, 11, 0, 43, 'desactivo'),
(685, 11, 0, 44, 'desactivo'),
(686, 11, 0, 45, 'desactivo'),
(687, 11, 0, 46, 'desactivo'),
(688, 11, 0, 47, 'desactivo'),
(689, 11, 0, 48, 'desactivo'),
(690, 11, 0, 49, 'desactivo'),
(691, 11, 0, 50, 'desactivo'),
(692, 11, 0, 51, 'desactivo'),
(693, 11, 0, 52, 'desactivo'),
(694, 11, 0, 53, 'desactivo'),
(695, 11, 0, 54, 'desactivo'),
(696, 11, 0, 55, 'desactivo'),
(697, 11, 0, 56, 'desactivo'),
(698, 11, 0, 57, 'desactivo'),
(699, 11, 0, 58, 'desactivo'),
(700, 11, 0, 59, 'desactivo'),
(701, 11, 0, 60, 'desactivo'),
(702, 11, 0, 61, 'desactivo'),
(703, 11, 0, 62, 'desactivo'),
(704, 11, 0, 63, 'desactivo'),
(705, 11, 0, 64, 'desactivo'),
(706, 11, 0, 65, 'desactivo'),
(707, 11, 0, 66, 'desactivo'),
(708, 11, 0, 67, 'desactivo'),
(709, 11, 0, 68, 'desactivo'),
(710, 11, 0, 69, 'desactivo'),
(711, 11, 0, 70, 'desactivo'),
(712, 11, 0, 71, 'desactivo'),
(713, 11, 0, 72, 'desactivo'),
(714, 11, 0, 73, 'desactivo'),
(715, 11, 0, 74, 'desactivo'),
(716, 11, 0, 75, 'desactivo'),
(717, 11, 0, 76, 'desactivo'),
(718, 11, 0, 77, 'desactivo'),
(719, 11, 0, 78, 'desactivo'),
(720, 11, 0, 79, 'desactivo'),
(721, 11, 0, 80, 'desactivo'),
(722, 11, 0, 81, 'desactivo'),
(723, 11, 0, 82, 'desactivo'),
(724, 11, 0, 83, 'desactivo'),
(725, 11, 0, 84, 'desactivo'),
(726, 11, 0, 85, 'desactivo'),
(727, 11, 0, 86, 'desactivo'),
(728, 11, 0, 87, 'desactivo'),
(729, 11, 0, 88, 'desactivo'),
(730, 11, 0, 89, 'desactivo'),
(731, 11, 0, 90, 'desactivo'),
(732, 11, 0, 91, 'desactivo'),
(733, 11, 0, 92, 'desactivo'),
(734, 11, 0, 93, 'desactivo'),
(735, 11, 0, 94, 'desactivo'),
(736, 11, 0, 95, 'desactivo'),
(737, 11, 0, 96, 'desactivo'),
(738, 11, 0, 97, 'desactivo'),
(739, 11, 0, 98, 'desactivo'),
(740, 11, 0, 99, 'desactivo'),
(741, 11, 0, 100, 'desactivo'),
(742, 11, 0, 101, 'desactivo'),
(743, 11, 0, 102, 'desactivo'),
(744, 11, 0, 103, 'desactivo'),
(745, 11, 0, 104, 'desactivo'),
(746, 11, 0, 105, 'desactivo'),
(747, 11, 0, 106, 'desactivo'),
(748, 11, 0, 107, 'desactivo'),
(749, 11, 0, 108, 'desactivo'),
(750, 11, 0, 109, 'desactivo'),
(751, 11, 0, 110, 'desactivo'),
(752, 11, 0, 111, 'desactivo'),
(753, 11, 0, 112, 'desactivo'),
(754, 11, 0, 113, 'desactivo'),
(755, 11, 0, 114, 'desactivo'),
(756, 11, 0, 115, 'desactivo'),
(757, 11, 0, 116, 'desactivo'),
(758, 11, 0, 117, 'desactivo'),
(759, 11, 0, 118, 'desactivo'),
(760, 11, 0, 119, 'desactivo'),
(761, 11, 0, 120, 'desactivo'),
(762, 11, 0, 121, 'desactivo'),
(763, 11, 0, 122, 'desactivo'),
(764, 11, 0, 123, 'desactivo'),
(765, 11, 0, 124, 'desactivo'),
(766, 11, 0, 125, 'desactivo'),
(767, 11, 0, 126, 'desactivo'),
(768, 11, 0, 127, 'desactivo'),
(769, 12, NULL, 0, 'desactivo'),
(770, 12, NULL, 1, 'desactivo'),
(771, 12, NULL, 2, 'desactivo'),
(772, 12, NULL, 3, 'desactivo'),
(773, 12, NULL, 4, 'desactivo'),
(774, 12, NULL, 5, 'desactivo'),
(775, 12, NULL, 6, 'desactivo'),
(776, 12, NULL, 7, 'desactivo'),
(777, 12, NULL, 8, 'desactivo'),
(778, 12, NULL, 9, 'desactivo'),
(779, 12, NULL, 10, 'desactivo'),
(780, 12, NULL, 11, 'desactivo'),
(781, 12, NULL, 12, 'desactivo'),
(782, 12, NULL, 13, 'desactivo'),
(783, 12, NULL, 14, 'desactivo'),
(784, 12, NULL, 15, 'desactivo'),
(785, 12, NULL, 16, 'desactivo'),
(786, 12, NULL, 17, 'desactivo'),
(787, 12, NULL, 18, 'desactivo'),
(788, 12, NULL, 19, 'desactivo'),
(789, 12, NULL, 20, 'desactivo'),
(790, 12, NULL, 21, 'desactivo'),
(791, 12, NULL, 22, 'desactivo'),
(792, 12, NULL, 23, 'desactivo'),
(793, 12, NULL, 24, 'desactivo'),
(794, 12, NULL, 25, 'desactivo'),
(795, 12, NULL, 26, 'desactivo'),
(796, 12, NULL, 27, 'desactivo'),
(797, 12, NULL, 28, 'desactivo'),
(798, 12, NULL, 29, 'desactivo'),
(799, 12, NULL, 30, 'desactivo'),
(800, 12, NULL, 31, 'desactivo'),
(801, 12, NULL, 32, 'desactivo'),
(802, 12, NULL, 33, 'desactivo'),
(803, 12, NULL, 34, 'desactivo'),
(804, 12, NULL, 35, 'desactivo'),
(805, 12, NULL, 36, 'desactivo'),
(806, 12, NULL, 37, 'desactivo'),
(807, 12, NULL, 38, 'desactivo'),
(808, 12, NULL, 39, 'desactivo'),
(809, 12, NULL, 40, 'desactivo'),
(810, 12, NULL, 41, 'desactivo'),
(811, 12, NULL, 42, 'desactivo'),
(812, 12, NULL, 43, 'desactivo'),
(813, 12, NULL, 44, 'desactivo'),
(814, 12, NULL, 45, 'desactivo'),
(815, 12, NULL, 46, 'desactivo'),
(816, 12, NULL, 47, 'desactivo'),
(817, 12, NULL, 48, 'desactivo'),
(818, 12, NULL, 49, 'desactivo'),
(819, 12, NULL, 50, 'desactivo'),
(820, 12, NULL, 51, 'desactivo'),
(821, 12, NULL, 52, 'desactivo'),
(822, 12, NULL, 53, 'desactivo'),
(823, 12, NULL, 54, 'desactivo'),
(824, 12, NULL, 55, 'desactivo'),
(825, 12, NULL, 56, 'desactivo'),
(826, 12, NULL, 57, 'desactivo'),
(827, 12, NULL, 58, 'desactivo'),
(828, 12, NULL, 59, 'desactivo'),
(829, 12, NULL, 60, 'desactivo'),
(830, 12, NULL, 61, 'desactivo'),
(831, 12, NULL, 62, 'desactivo'),
(832, 12, NULL, 63, 'desactivo'),
(833, 12, NULL, 64, 'desactivo'),
(834, 12, NULL, 65, 'desactivo'),
(835, 12, NULL, 66, 'desactivo'),
(836, 12, NULL, 67, 'desactivo'),
(837, 12, NULL, 68, 'desactivo'),
(838, 12, NULL, 69, 'desactivo'),
(839, 12, NULL, 70, 'desactivo'),
(840, 12, NULL, 71, 'desactivo'),
(841, 12, NULL, 72, 'desactivo'),
(842, 12, NULL, 73, 'desactivo'),
(843, 12, NULL, 74, 'desactivo'),
(844, 12, NULL, 75, 'desactivo'),
(845, 12, NULL, 76, 'desactivo'),
(846, 12, NULL, 77, 'desactivo'),
(847, 12, NULL, 78, 'desactivo'),
(848, 12, NULL, 79, 'desactivo'),
(849, 12, NULL, 80, 'desactivo'),
(850, 12, NULL, 81, 'desactivo'),
(851, 12, NULL, 82, 'desactivo'),
(852, 12, NULL, 83, 'desactivo'),
(853, 12, NULL, 84, 'desactivo'),
(854, 12, NULL, 85, 'desactivo'),
(855, 12, NULL, 86, 'desactivo'),
(856, 12, NULL, 87, 'desactivo'),
(857, 12, NULL, 88, 'desactivo'),
(858, 12, NULL, 89, 'desactivo'),
(859, 12, NULL, 90, 'desactivo'),
(860, 12, NULL, 91, 'desactivo'),
(861, 12, NULL, 92, 'desactivo'),
(862, 12, NULL, 93, 'desactivo'),
(863, 12, NULL, 94, 'desactivo'),
(864, 12, NULL, 95, 'desactivo'),
(865, 12, NULL, 96, 'desactivo'),
(866, 12, NULL, 97, 'desactivo'),
(867, 12, NULL, 98, 'desactivo'),
(868, 12, NULL, 99, 'desactivo'),
(869, 12, NULL, 100, 'desactivo'),
(870, 12, NULL, 101, 'desactivo'),
(871, 12, NULL, 102, 'desactivo'),
(872, 12, NULL, 103, 'desactivo'),
(873, 12, NULL, 104, 'desactivo'),
(874, 12, NULL, 105, 'desactivo'),
(875, 12, NULL, 106, 'desactivo'),
(876, 12, NULL, 107, 'desactivo'),
(877, 12, NULL, 108, 'desactivo'),
(878, 12, NULL, 109, 'desactivo'),
(879, 12, NULL, 110, 'desactivo'),
(880, 12, NULL, 111, 'desactivo'),
(881, 12, NULL, 112, 'desactivo'),
(882, 12, NULL, 113, 'desactivo'),
(883, 12, NULL, 114, 'desactivo'),
(884, 12, NULL, 115, 'desactivo'),
(885, 12, NULL, 116, 'desactivo'),
(886, 12, NULL, 117, 'desactivo'),
(887, 12, NULL, 118, 'desactivo'),
(888, 12, NULL, 119, 'desactivo'),
(889, 12, NULL, 120, 'desactivo'),
(890, 12, NULL, 121, 'desactivo'),
(891, 12, NULL, 122, 'desactivo'),
(892, 12, NULL, 123, 'desactivo'),
(893, 12, NULL, 124, 'desactivo'),
(894, 12, NULL, 125, 'desactivo'),
(895, 12, NULL, 126, 'desactivo'),
(896, 12, NULL, 127, 'desactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_rol` int(11) NOT NULL,
  `nombrerol_rol` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `nombrerol_rol`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'TECNICO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `usuario_usuario` varchar(50) NOT NULL,
  `contraseña_usuario` varchar(50) NOT NULL,
  `id_rol` int(11) NOT NULL,
  `estado_usuario` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre_usuario`, `usuario_usuario`, `contraseña_usuario`, `id_rol`, `estado_usuario`) VALUES
(1, 'Jonathan Pulla', 'pulla', 'admin', 1, 'activo'),
(2, 'admin25', 'admin25', 'admin25', 2, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vlan`
--

CREATE TABLE `vlan` (
  `id_vlan` int(11) NOT NULL,
  `numerovlan_vlan` int(11) NOT NULL,
  `nombre_vlan` varchar(50) NOT NULL,
  `numeroolt_vlan` int(11) NOT NULL,
  `tarjetaolt_vlan` int(11) NOT NULL,
  `puertoolt_vlan` int(11) NOT NULL,
  `ipinicio_vlan` varchar(50) NOT NULL,
  `ipfin_vlan` varchar(50) NOT NULL,
  `mascara_vlan` varchar(50) NOT NULL,
  `gateway_vlan` varchar(50) NOT NULL,
  `estado_vlan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `vlan`
--

INSERT INTO `vlan` (`id_vlan`, `numerovlan_vlan`, `nombre_vlan`, `numeroolt_vlan`, `tarjetaolt_vlan`, `puertoolt_vlan`, `ipinicio_vlan`, `ipfin_vlan`, `mascara_vlan`, `gateway_vlan`, `estado_vlan`) VALUES
(10, 1000, 'Vlan 1000', 0, 1, 2, '10.170.5.2', '10.170.5.10', '255.255.255.128', '10.170.5.1', 'activo'),
(11, 2000, 'Vlan 2000', 0, 2, 3, '10.170.10.5', '10.170.10.9', '255.255.255.128', '10.170.10.9', 'activo'),
(12, 300, 'Vlan 300', 0, 2, 3, '10.170.5.6', '10.170.5.8', '255.255.255.128', '10.170.6.3', 'activo');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cajanivel1`
--
ALTER TABLE `cajanivel1`
  ADD PRIMARY KEY (`id_cajanivel1`),
  ADD KEY `id_ciudad` (`id_ciudad`),
  ADD KEY `id_vlan` (`id_vlan`) USING BTREE;

--
-- Indices de la tabla `cajanivel2`
--
ALTER TABLE `cajanivel2`
  ADD PRIMARY KEY (`id_cajanivel2`),
  ADD KEY `id_cajanivel1` (`id_cajanivel1`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id_ciudad`),
  ADD KEY `id_provincia` (`id_provincia`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `id_direcionip` (`id_cajanivel2`),
  ADD KEY `id_clientepersona` (`id_clientepersona`),
  ADD KEY `id_ont` (`id_ont`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_cajanivel2` (`id_cajanivel2`),
  ADD KEY `id_planes` (`id_planes`);

--
-- Indices de la tabla `clientepersona`
--
ALTER TABLE `clientepersona`
  ADD PRIMARY KEY (`id_clientepersona`);

--
-- Indices de la tabla `idsclienteslibres`
--
ALTER TABLE `idsclienteslibres`
  ADD PRIMARY KEY (`id_idsclienteslibres`);

--
-- Indices de la tabla `modelosont`
--
ALTER TABLE `modelosont`
  ADD PRIMARY KEY (`id_modelosont`);

--
-- Indices de la tabla `ont`
--
ALTER TABLE `ont`
  ADD PRIMARY KEY (`id_ont`),
  ADD KEY `id_modelo` (`id_modelo`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id_pais`);

--
-- Indices de la tabla `planes`
--
ALTER TABLE `planes`
  ADD PRIMARY KEY (`id_planes`);

--
-- Indices de la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD PRIMARY KEY (`id_provincia`),
  ADD KEY `id_pais` (`id_pais`);

--
-- Indices de la tabla `rangodireccionesip`
--
ALTER TABLE `rangodireccionesip`
  ADD PRIMARY KEY (`id_rangodireccionesip`),
  ADD KEY `id_vlan` (`id_vlan`);

--
-- Indices de la tabla `rangohiloscaja1`
--
ALTER TABLE `rangohiloscaja1`
  ADD PRIMARY KEY (`id_rangohiloscaja1`),
  ADD KEY `id_cajanivel1` (`id_cajanivel1`);

--
-- Indices de la tabla `rangohiloscaja2`
--
ALTER TABLE `rangohiloscaja2`
  ADD PRIMARY KEY (`id_rangohiloscaja2`),
  ADD KEY `id_cajanivel2` (`id_cajanivel2`);

--
-- Indices de la tabla `rangoont`
--
ALTER TABLE `rangoont`
  ADD PRIMARY KEY (`id_rangoont`),
  ADD KEY `idvlan_rangoont` (`id_vlan`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `vlan`
--
ALTER TABLE `vlan`
  ADD PRIMARY KEY (`id_vlan`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cajanivel1`
--
ALTER TABLE `cajanivel1`
  MODIFY `id_cajanivel1` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `cajanivel2`
--
ALTER TABLE `cajanivel2`
  MODIFY `id_cajanivel2` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id_ciudad` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `clientepersona`
--
ALTER TABLE `clientepersona`
  MODIFY `id_clientepersona` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `idsclienteslibres`
--
ALTER TABLE `idsclienteslibres`
  MODIFY `id_idsclienteslibres` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `modelosont`
--
ALTER TABLE `modelosont`
  MODIFY `id_modelosont` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `ont`
--
ALTER TABLE `ont`
  MODIFY `id_ont` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id_pais` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `planes`
--
ALTER TABLE `planes`
  MODIFY `id_planes` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `provincia`
--
ALTER TABLE `provincia`
  MODIFY `id_provincia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `rangodireccionesip`
--
ALTER TABLE `rangodireccionesip`
  MODIFY `id_rangodireccionesip` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT de la tabla `rangohiloscaja1`
--
ALTER TABLE `rangohiloscaja1`
  MODIFY `id_rangohiloscaja1` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `rangohiloscaja2`
--
ALTER TABLE `rangohiloscaja2`
  MODIFY `id_rangohiloscaja2` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=101;

--
-- AUTO_INCREMENT de la tabla `rangoont`
--
ALTER TABLE `rangoont`
  MODIFY `id_rangoont` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=897;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `vlan`
--
ALTER TABLE `vlan`
  MODIFY `id_vlan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `cajanivel1`
--
ALTER TABLE `cajanivel1`
  ADD CONSTRAINT `cajanivel1_ibfk_2` FOREIGN KEY (`id_ciudad`) REFERENCES `ciudad` (`id_ciudad`) ON DELETE CASCADE,
  ADD CONSTRAINT `cajanivel1_ibfk_3` FOREIGN KEY (`id_vlan`) REFERENCES `vlan` (`id_vlan`) ON DELETE CASCADE;

--
-- Filtros para la tabla `cajanivel2`
--
ALTER TABLE `cajanivel2`
  ADD CONSTRAINT `cajanivel2_ibfk_1` FOREIGN KEY (`id_cajanivel1`) REFERENCES `cajanivel1` (`id_cajanivel1`) ON DELETE CASCADE;

--
-- Filtros para la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD CONSTRAINT `ciudad_ibfk_1` FOREIGN KEY (`id_provincia`) REFERENCES `provincia` (`id_provincia`) ON DELETE CASCADE;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `cliente_ibfk_11` FOREIGN KEY (`id_clientepersona`) REFERENCES `clientepersona` (`id_clientepersona`) ON DELETE CASCADE,
  ADD CONSTRAINT `cliente_ibfk_12` FOREIGN KEY (`id_planes`) REFERENCES `planes` (`id_planes`) ON DELETE CASCADE,
  ADD CONSTRAINT `cliente_ibfk_13` FOREIGN KEY (`id_cajanivel2`) REFERENCES `cajanivel2` (`id_cajanivel2`) ON DELETE CASCADE,
  ADD CONSTRAINT `cliente_ibfk_6` FOREIGN KEY (`id_ont`) REFERENCES `ont` (`id_ont`),
  ADD CONSTRAINT `cliente_ibfk_7` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `ont`
--
ALTER TABLE `ont`
  ADD CONSTRAINT `ont_ibfk_1` FOREIGN KEY (`id_modelo`) REFERENCES `modelosont` (`id_modelosont`) ON DELETE CASCADE;

--
-- Filtros para la tabla `provincia`
--
ALTER TABLE `provincia`
  ADD CONSTRAINT `provincia_ibfk_1` FOREIGN KEY (`id_pais`) REFERENCES `pais` (`id_pais`) ON DELETE CASCADE;

--
-- Filtros para la tabla `rangodireccionesip`
--
ALTER TABLE `rangodireccionesip`
  ADD CONSTRAINT `rangodireccionesip_ibfk_1` FOREIGN KEY (`id_vlan`) REFERENCES `vlan` (`id_vlan`) ON DELETE CASCADE;

--
-- Filtros para la tabla `rangohiloscaja1`
--
ALTER TABLE `rangohiloscaja1`
  ADD CONSTRAINT `rangohiloscaja1_ibfk_1` FOREIGN KEY (`id_cajanivel1`) REFERENCES `cajanivel1` (`id_cajanivel1`) ON DELETE CASCADE;

--
-- Filtros para la tabla `rangohiloscaja2`
--
ALTER TABLE `rangohiloscaja2`
  ADD CONSTRAINT `rangohiloscaja2_ibfk_1` FOREIGN KEY (`id_cajanivel2`) REFERENCES `cajanivel2` (`id_cajanivel2`) ON DELETE CASCADE;

--
-- Filtros para la tabla `rangoont`
--
ALTER TABLE `rangoont`
  ADD CONSTRAINT `rangoont_ibfk_1` FOREIGN KEY (`id_vlan`) REFERENCES `vlan` (`id_vlan`) ON DELETE CASCADE;

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `rol` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
