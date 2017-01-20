--
-- Insercion de datos de Tipos de Usuarios
--
INSERT INTO `tipo_usuarios`
(`ID`, `descripcion`) 
VALUES 
(1, 'Administrador'),
(2, 'Empleado');

--
-- Insercion de datos de Usuarios
--
INSERT INTO `usuarios`
(`ID`, `clave`, `nombre`, `id_tipoUsuario`) 
VALUES 
(1, 'Admin', 'Admin', 1),
(2, 'cajero', 'cajero', 2);