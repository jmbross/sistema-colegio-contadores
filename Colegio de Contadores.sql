CREATE DATABASE IF NOT EXISTS gestion_sueldos;
USE gestion_sueldos;


CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  dni VARCHAR(10) NOT NULL,
  matricula VARCHAR(20),
  email VARCHAR(50) NOT NULL,
  contrasena VARCHAR(100) NOT NULL,
  rol ENUM('admin', 'usuario') NOT NULL,
  PRIMARY KEY (id_usuario)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE trabajador (
  id_trabajador INT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  apellido VARCHAR(50) NOT NULL,
  dni VARCHAR(10) NOT NULL,
  sueldo_bruto DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_trabajador)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Agregar las columnas 'email' y 'telefono' a la tabla 'trabajador'
ALTER TABLE trabajador
ADD COLUMN email VARCHAR(50),
ADD COLUMN telefono VARCHAR(15);

ALTER TABLE trabajador
ADD COLUMN id_usuario INT,
ADD CONSTRAINT fk_usuario
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
  ON DELETE CASCADE;



CREATE TABLE alicuota (
  id_alicuota INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(100) NOT NULL,
  porcentaje DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (id_alicuota)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
ALTER TABLE alicuota
ADD COLUMN id_trabajador INT,
ADD CONSTRAINT fk_trabajador
  FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador)
  ON DELETE CASCADE;


CREATE TABLE recibo (
  id_recibo INT NOT NULL AUTO_INCREMENT,
  id_trabajador INT NOT NULL,
  fecha DATE NOT NULL,
  sueldo_neto DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (id_recibo),
  FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE usuario_trabajador (
  id_usuario_trabajador INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  id_trabajador INT NOT NULL,
  PRIMARY KEY (id_usuario_trabajador),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  FOREIGN KEY (id_trabajador) REFERENCES trabajador(id_trabajador)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

------------------------------------------------

-- Insertar 25 trabajadores (5 por cada usuario) en la tabla trabajador
INSERT INTO trabajador (nombre, apellido, dni, sueldo_bruto, email, telefono, id_usuario)
VALUES
-- Trabajadores del usuario 1
('Andrés', 'López', '12345678', 50000.00, 'andres.lopez@example.com', '1123456789', 1),
('Marta', 'Fernández', '23456789', 52000.00, 'marta.fernandez@example.com', '1123456790', 1),
('Pablo', 'Rodríguez', '34567890', 53000.00, 'pablo.rodriguez@example.com', '1123456791', 1),
('Ana', 'García', '45678901', 54000.00, 'ana.garcia@example.com', '1123456792', 1),
('David', 'Sánchez', '56789012', 51000.00, 'david.sanchez@example.com', '1123456793', 1),

-- Trabajadores del usuario 2
('Beatriz', 'Vega', '67890123', 60000.00, 'beatriz.vega@example.com', '1123456794', 2),
('Luis', 'Gómez', '78901234', 62000.00, 'luis.gomez@example.com', '1123456795', 2),
('Carolina', 'Martínez', '89012345', 61000.00, 'carolina.martinez@example.com', '1123456796', 2),
('Raúl', 'Herrera', '90123456', 60500.00, 'raul.herrera@example.com', '1123456797', 2),
('Sandra', 'Navarro', '01234567', 61500.00, 'sandra.navarro@example.com', '1123456798', 2),

-- Trabajadores del usuario 3
('Gustavo', 'Ramos', '12345098', 70000.00, 'gustavo.ramos@example.com', '1123456799', 3),
('Claudia', 'Méndez', '23456098', 72000.00, 'claudia.mendez@example.com', '1123456800', 3),
('Sergio', 'Morales', '34567098', 71000.00, 'sergio.morales@example.com', '1123456801', 3),
('Natalia', 'Torres', '45678098', 73000.00, 'natalia.torres@example.com', '1123456802', 3),
('Enrique', 'Suárez', '56789098', 74000.00, 'enrique.suarez@example.com', '1123456803', 3),

-- Trabajadores del usuario 4
('Paula', 'Cabrera', '67890187', 80000.00, 'paula.cabrera@example.com', '1123456804', 4),
('Tomás', 'Flores', '78901287', 82000.00, 'tomas.flores@example.com', '1123456805', 4),
('Elena', 'Romero', '89012387', 81000.00, 'elena.romero@example.com', '1123456806', 4),
('Gabriel', 'Rivas', '90123487', 83000.00, 'gabriel.rivas@example.com', '1123456807', 4),
('Verónica', 'Campos', '01234587', 84000.00, 'veronica.campos@example.com', '1123456808', 4),

-- Trabajadores del usuario 5
('Ignacio', 'Silva', '12345676', 90000.00, 'ignacio.silva@example.com', '1123456809', 5),
('Victoria', 'Delgado', '23456776', 92000.00, 'victoria.delgado@example.com', '1123456810', 5),
('Miguel', 'Soto', '34567876', 91000.00, 'miguel.soto@example.com', '1123456811', 5),
('Rocío', 'Correa', '45678976', 93000.00, 'rocio.correa@example.com', '1123456812', 5),
('Esteban', 'Aguilar', '56789076', 94000.00, 'esteban.aguilar@example.com', '1123456813', 5);

-----------------------------------------------------------------------

-- Insertar alícuotas para cada trabajador
INSERT INTO alicuota (descripcion, porcentaje, id_trabajador) VALUES
('Alicuota 1 - Salud', 5.00, 1),
('Alicuota 2 - Jubilación', 3.00, 1),
('Alicuota 3 - Fondo de Vivienda', 2.00, 1),
('Alicuota 1 - Salud', 5.00, 2),
('Alicuota 2 - Jubilación', 3.00, 2),
('Alicuota 3 - Fondo de Vivienda', 2.00, 2),
('Alicuota 1 - Salud', 5.00, 3),
('Alicuota 2 - Jubilación', 3.00, 3),
('Alicuota 3 - Fondo de Vivienda', 2.00, 3),
('Alicuota 1 - Salud', 5.00, 4),
('Alicuota 2 - Jubilación', 3.00, 4),
('Alicuota 3 - Fondo de Vivienda', 2.00, 4),
('Alicuota 1 - Salud', 5.00, 5),
('Alicuota 2 - Jubilación', 3.00, 5),
('Alicuota 3 - Fondo de Vivienda', 2.00, 5);
--------------------------------------------------------------


-- Ejemplos de Consultas Combinando Tablas
-- Listar Trabajadores con sus Usuarios Asociados
SELECT t.id_trabajador, t.nombre AS nombre_trabajador, t.apellido AS apellido_trabajador, u.nombre AS nombre_usuario, u.apellido AS apellido_usuario
FROM trabajador t
JOIN usuario u ON t.id_usuario = u.id_usuario;


-- Obtener Alícuotas de un Trabajador Específico
SELECT a.id_alicuota, a.descripcion, a.porcentaje
FROM alicuota a
JOIN trabajador t ON a.id_trabajador = t.id_trabajador
WHERE t.id_trabajador = 1; -- Cambia el ID según sea necesario

-- Listar Recibos de Sueldo con Información del Trabajador
SELECT r.id_recibo, r.fecha, r.sueldo_neto, t.nombre, t.apellido
FROM recibo r
JOIN trabajador t ON r.id_trabajador = t.id_trabajador;

-- Contar el Número de Trabajadores por Usuario
SELECT u.id_usuario, u.nombre, u.apellido, COUNT(t.id_trabajador) AS numero_trabajadores
FROM usuario u
LEFT JOIN trabajador t ON u.id_usuario = t.id_usuario
GROUP BY u.id_usuario;

-- Listar Trabajadores y sus Alícuotas
SELECT t.nombre AS nombre_trabajador, t.apellido AS apellido_trabajador, a.descripcion, a.porcentaje
FROM trabajador t
LEFT JOIN alicuota a ON t.id_trabajador = a.id_trabajador
ORDER BY t.apellido;



-- Ejemplos de Borrado de Registros
-- Borrar un Usuario Específico y sus Trabajadores Asociados
DELETE FROM usuario WHERE id_usuario = 1; 

-- Borrar un Trabajador Específico
DELETE FROM trabajador WHERE id_trabajador = 1; -- Cambia el ID según sea necesario

-- Borrar Alícuotas de un Trabajador Específico
DELETE FROM alicuota WHERE id_trabajador = 1; -- Cambia el ID según sea necesario

-- Borrar un Recibo de Sueldo Específico
DELETE FROM recibo WHERE id_recibo = 1; -- Cambia el ID según sea necesario

-- Borrar la Relación entre Usuario y Trabajador
DELETE FROM usuario_trabajador WHERE id_usuario_trabajador = 1; -- Cambia el ID según sea 
