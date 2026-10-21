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
                        .title("Foro Hub API")
                        .description("API REST de un foro de discusión: autenticación con JWT y gestión de tópicos (registro, consulta paginada, actualización y borrado lógico).")
                        .contact(new Contact()
                                .name("Brayant Vásquez Gómez")
                                .url("https://github.com/iBrayant"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/license/mit")));
    }
} 