# Foro Alura - API REST

API REST desarrollada para el foro de Alura, permitiendo la gestión de tópicos de discusión.

## 🚀 Características

- Autenticación con JWT
- CRUD completo de tópicos
- Paginación y ordenamiento
- Validaciones y manejo de errores
- Documentación con SpringDoc OpenAPI

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.2.1
- Spring Security
- JWT
- MySQL
- Flyway para migraciones
- Maven

## 📋 Requisitos

- Java 17 o superior
- MySQL 8
- Maven

## ⚙️ Configuración

1. Clonar el repositorio
2. Crear base de datos MySQL:
```sql
CREATE DATABASE foro_alura;
```

3. Configurar application.properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foro_alura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

## 🚀 Endpoints

### Autenticación

```http
POST /login
```
```json
{
    "email": "admin@mail.com",
    "password": "123456"
}
```

### Tópicos

```http
# Listar tópicos (paginado)
GET /topicos?size=10&page=0&sort=fechaCreacion,desc

# Crear tópico
POST /topicos
{
    "titulo": "Título del tópico",
    "mensaje": "Mensaje del tópico",
    "autorId": 1,
    "cursoId": 1
}

# Obtener tópico por ID
GET /topicos/{id}

# Actualizar tópico
PUT /topicos
{
    "id": 1,
    "titulo": "Nuevo título",
    "mensaje": "Nuevo mensaje"
}

# Eliminar tópico
DELETE /topicos/{id}
```

## 🔒 Seguridad

- Todos los endpoints (excepto /login) requieren autenticación
- Usar el token JWT en el header: `Authorization: Bearer {token}`

## 📝 Documentación

La documentación completa de la API está disponible en:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 🗃️ Base de Datos

El esquema incluye las siguientes tablas:
- usuarios
- topicos
- cursos

Las migraciones se ejecutan automáticamente al iniciar la aplicación.

## 🧪 Datos de Prueba

Usuario por defecto:
- Email: admin@mail.com
- Contraseña: 123456 