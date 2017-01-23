--
-- Insercion de datos de Zonas
--
INSERT INTO `zonas` 
(`ID`, `nombre`) 
VALUES 
(1, 'NOROESTE'),
(2, 'NORESTE'),
(3, 'CUYO'),
(4, 'PAMPEANA'),
(5, 'PATAGONIA');


--
-- Insercion de datos de Provincia
--

INSERT INTO `provincias`
(`ID`, `nombre`, `ZONA_ID`) 
VALUES
(1, 'Buenos Aires-GBA', 4),
(2, 'Capital Federal', 4),
(3, 'Catamarca', 1),
(4, 'Chaco', 2),
(5, 'Chubut', 5),
(6, 'Córdoba', 4),
(7, 'Corrientes', 2),
(8, 'Entre Ríos', 2),
(9, 'Formosa', 2),
(10, 'Jujuy', 1),
(11, 'La Pampa', 4),
(12, 'La Rioja', 1),
(13, 'Mendoza', 3),
(14, 'Misiones', 2),
(15, 'Neuquén', 5),
(16, 'Río Negro', 5),
(17, 'Salta', 1),
(18, 'San Juan', 3),
(19, 'San Luis', 3),
(20, 'Santa Cruz', 5),
(21, 'Santa Fe', 4),
(22, 'Santiago del Estero', 1),
(23, 'Tierra del Fuego', 5),
(24, 'Tucumán', 1);

--
-- Insercion de datos de Zonas_Provincias
--
INSERT INTO `zonas_provincias`
(`Zona_ID`, `provincias_ID`) 
VALUES 
(4, 1),
(4, 2),
(1, 3),
(2, 4),
(5, 5),
(4, 6),
(2, 7),
(2, 8),
(2, 9),
(1, 10),
(4, 11),
(1, 12),
(3, 13),
(2, 14),
(5, 15),
(5, 16),
(1, 17),
(3, 18),
(3, 19),
(5, 20),
(4, 21),
(1, 22),
(5, 23),
(1, 24);