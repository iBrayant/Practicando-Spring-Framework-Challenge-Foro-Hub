CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(300) NOT NULL,
    activo TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

CREATE TABLE cursos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(100) NOT NULL,
    activo TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
);

CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    activo TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    FOREIGN KEY (autor_id) REFERENCES usuarios (id),
    FOREIGN KEY (curso_id) REFERENCES cursos (id)
); 