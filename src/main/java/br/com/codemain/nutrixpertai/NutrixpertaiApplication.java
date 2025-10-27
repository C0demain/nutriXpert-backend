package br.com.codemain.nutrixpertai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NutrixpertaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutrixpertaiApplication.class, args);
	}

}
