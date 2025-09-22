package br.com.codemain.nutrixpertai.infra.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "NutriXpert AI API",
        version = "v1.0",
        description = "API para gerenciamento de usuários, hábitos e recomendações."
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local"),
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    description = "Insira o token JWT no formato: Bearer {seu_token}",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
    
}
