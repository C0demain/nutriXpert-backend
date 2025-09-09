package br.com.codemain.nutrixpertai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

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
@SpringBootApplication
public class NutrixpertaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutrixpertaiApplication.class, args);
	}

}
