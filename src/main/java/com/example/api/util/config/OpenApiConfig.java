package com.example.api.util.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("RESTful API with Java 19 and Spring Boot 3.0.2")
                .version("v1")
                .description("Curso Udemy Spring Boot AWS")
                .termsOfService("https://google.com")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("https://google.com/license")
                )
            );
    }
    
}
