-- Insertar usuarios de prueba (password: 123456)
INSERT INTO usuarios (nombre, email, password, activo) 
VALUES ('Admin User', 'admin@mail.com', '$2a$10$H2N73MaToYc4T5U8RNBN5eGybZwxHsWg7Uyx0zAGVy1nZBXGUbARK', 1);

INSERT INTO usuarios (nombre, email, password, activo)
VALUES ('Test User', 'test@mail.com', '$2a$10$H2N73MaToYc4T5U8RNBN5eGybZwxHsWg7Uyx0zAGVy1nZBXGUbARK', 1);

-- Insertar cursos de prueba
INSERT INTO cursos (nombre, categoria, activo)
VALUES ('Spring Boot 3', 'Programación', 1);

INSERT INTO cursos (nombre, categoria, activo)
VALUES ('Java Básico', 'Programación', 1);

INSERT INTO cursos (nombre, categoria, activo)
VALUES ('MySQL', 'Base de datos', 1);

-- Insertar tópicos de prueba
INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor_id, curso_id, activo)
VALUES ('Duda sobre Spring', 'Tengo una duda sobre Spring Security', NOW(), 'NO_RESPONDIDO', 1, 1, 1);

INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor_id, curso_id, activo)
VALUES ('Error en Java', 'No puedo configurar mi JDK', NOW(), 'NO_SOLUCIONADO', 2, 2, 1);

INSERT INTO topicos (titulo, mensaje, fecha_creacion, status, autor_id, curso_id, activo)
VALUES ('Consulta MySQL', 'Cómo optimizar consultas', NOW(), 'SOLUCIONADO', 1, 3, 1); 