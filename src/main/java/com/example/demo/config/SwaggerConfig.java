package com.example.demo.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String securityScheme = "BearerScheme";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(securityScheme)
                )
                .components(new Components()
                        .addSecuritySchemes(securityScheme, new SecurityScheme()
                                .name(securityScheme)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                        )
                )
                .info(new Info()
                        .title("Blogging Application Backend API")
                        .description("This is a backend api project for a blogging application developed by Ankit Chauhan.")
                        .version("1.0")
                        .contact(new Contact().name("Ankit Chauhan")
                                .email("ankit2211chuhan@gmail.com")
                        )
                        .license(new License().name("Apache"))
                );

    }

}
