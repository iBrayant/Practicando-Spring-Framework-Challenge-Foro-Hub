package com.alura.foro.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Foro Alura")
                        .description("API REST de la aplicación Foro Alura, que contiene las funcionalidades de CRUD de tópicos, además de permitir la autenticación de usuarios.")
                        .contact(new Contact()
                                .name("Equipo Backend")
                                .email("backend@alura.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://foro.alura/api/licencia")));
    }
} 