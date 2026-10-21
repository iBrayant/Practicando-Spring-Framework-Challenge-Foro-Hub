# Foro Hub — API REST

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-000000?logo=jsonwebtokens&logoColor=white)
![Licencia](https://img.shields.io/badge/licencia-MIT-green)

API REST para gestionar los tópicos de un foro de discusión: registro, consulta con paginación, actualización y borrado lógico, protegida con autenticación JWT.

> Challenge **Foro Hub** del programa Oracle Next Education (Alura Latam), Back-end con Spring Boot.

<!-- TODO: captura de Swagger UI (guardar en docs/img/) -->
<!-- ![Swagger](docs/img/swagger.png) -->

## Características

- **Autenticación con JWT** (Spring Security, sin estado): el token expira a las 2 horas.
- **CRUD de tópicos** con borrado lógico: eliminar un tópico lo marca como inactivo y no lo borra.
- **Paginación y ordenamiento** en el listado, con filtro opcional por nombre de curso.
- **Validaciones** con Bean Validation y manejo centralizado de errores.
- **Regla de negocio:** no permite registrar dos tópicos con el mismo título y mensaje.
- **Migraciones con Flyway** que crean las tablas y cargan datos de prueba.
- **Documentación interactiva** con SpringDoc (Swagger UI).

## Stack

Java 17 · Spring Boot 3.2.1 · Spring Web · Spring Data JPA · Spring Security · java-jwt · MySQL · Flyway · SpringDoc OpenAPI · Lombok · Maven

## Arquitectura

```
com.alura.foro
├── controller/      -> endpoints REST (Autenticación, Tópicos)
├── domain/
│   ├── topico/      -> entidad, DTOs, repositorio y servicio
│   ├── usuario/     -> usuario, autenticación y repositorio
│   ├── curso/       -> curso y repositorio
│   └── enums/       -> estado del tópico
└── infra/
    ├── security/    -> configuración de Spring Security, filtro y servicio de tokens
    ├── errores/     -> tratador global de errores
    ├── springdoc/   -> configuración de Swagger
    └── config/      -> configuración de JPA
```

## Cómo correrlo

Requisitos: Java 17+, Maven y MySQL 8.

1. Crea la base de datos:

   ```sql
   CREATE DATABASE foro_alura;
   ```

2. Ajusta la conexión en `src/main/resources/application.properties` (`spring.datasource.url`, `username` y `password`).

3. Define el secreto con el que se firman los tokens:

   ```bash
   export JWT_SECRET="una-clave-larga-y-aleatoria"
   ```

4. Arranca la aplicación (Flyway crea las tablas y los datos de prueba):

   ```bash
   mvn spring-boot:run
   ```

La API queda en http://localhost:8080 y Swagger UI en http://localhost:8080/swagger-ui.html.

## Endpoints

| Método | Ruta | Descripción | Autenticación |
|---|---|---|---|
| POST | `/login` | Devuelve un token JWT | No |
| POST | `/topicos` | Registra un tópico | Sí |
| GET | `/topicos` | Lista tópicos (paginado) | Sí |
| GET | `/topicos/{id}` | Detalla un tópico | Sí |
| PUT | `/topicos` | Actualiza título y mensaje | Sí |
| DELETE | `/topicos/{id}` | Borrado lógico | Sí |

Los endpoints protegidos requieren el header `Authorization: Bearer {token}`.

**Autenticación**

```http
POST /login
```
```json
{ "email": "admin@mail.com", "password": "123456" }
```

**Crear un tópico**

```http
POST /topicos
```
```json
{
  "titulo": "Título del tópico",
  "mensaje": "Mensaje del tópico",
  "autorId": 1,
  "cursoId": 1
}
```

**Listar con paginación, orden y filtro por curso**

```http
GET /topicos?size=10&page=0&sort=fechaCreacion,desc&nombreCurso=Spring Boot 3
```

**Actualizar**

```http
PUT /topicos
```
```json
{ "id": 1, "titulo": "Nuevo título", "mensaje": "Nuevo mensaje" }
```

## Base de datos

Tablas: `usuarios`, `cursos` y `topicos`. La migración `V2` carga datos solo para desarrollo: dos usuarios (`admin@mail.com` y `test@mail.com`, contraseña `123456`), tres cursos y tres tópicos. No uses estos usuarios fuera de un entorno local.

## Autor

**Brayant Vásquez Gómez** · [GitHub](https://github.com/iBrayant) · [LinkedIn](https://linkedin.com/in/brayant-vásquez-gómez)

Licencia [MIT](LICENSE).
