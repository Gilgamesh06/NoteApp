-- Esquema de la base de datos de NoteApp
-- La base de datos se llamo note_app
-- Se esta usando PosgreSQL

CREATE TABLE persona(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    apellido VARCHAR(200),
    correo_electronico VARCHAR(200) UNIQUE NOT NULL,
    fecha_nacimiento  DATE NOT NULL
);


CREATE TABLE usuario(
    id SERIAL PRIMARY KEY,
    nickname VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(150) NOT NULL,
    persona_id INT UNIQUE NOT NULL, -- se usa unique para la relacion uno a uno

    CONSTRAINT fk_persona_id FOREIGN KEY (persona_id) REFERENCES persona(id) 

);

CREATE TABLE nota(
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100) UNIQUE NOT NULL,
    descripcion VARCHAR(255),
    estado BOOLEAN NOT NULL,
    usuario_id  INT NOT NULL,

    CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id) 
);

