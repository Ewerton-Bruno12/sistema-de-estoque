package com.ewerton.sistema_de_estoque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SistemaDeEstoqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemaDeEstoqueApplication.class, args);
	}

}
