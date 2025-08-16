
-- datos de la entidad persona  5 Personas
INSERT INTO persona(nombre,apellido,correo_electronico,fecha_nacimiento) VALUES
                   ('Juan', 'Pérez', 'juan.perez@example.com', '1990-05-15'),
                   ('María', 'Gómez', 'maria.gomez@example.com', '1985-11-22'),
                   ('Carlos', 'Rodríguez', 'carlos.rodriguez@example.com', '1992-03-30'),
                   ('Ana', 'Martínez', 'ana.martinez@example.com', '1988-07-08'),
                   ('Luis', 'Fernández', 'luis.fernandez@example.com', '1995-12-01');

-- datos de la entidad usuario 5 usuario relacion uno a uno con persona
INSERT INTO usuario(nickname, password, persona_id) VALUES
                   ('Juan', 'Contraseña123',1),
                   ('María', 'MiClaveSegura!',2),
                   ('Carlos', 'Passw0rd!',3),
                   ('Ana', '1234Abcd!',4),
                   ('Luis', 'Seguridad202',5);


-- datos de la entidad nota relacion uno a muchos con la entidad usuario
INSERT INTO nota(titulo,descripcion,estado,usuario_id) VALUES
                ('Recordatorio', 'Compra pan y leche',TRUE,1),
                ('Estudiar', 'Estudiar SpringBoot y Angular', TRUE, 3),
                ('Java', 'Repasar Programacion funcional', TRUE, 3),
                ('Hacer Aseo', 'Lavar el baño y trapear', FLASE, 2);