package com.example.cesurspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
/**
 * Clase principal de la aplicación Spring Boot.
 */
public class CesurSpringBootApplication {
	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 *
	 * @param args Argumentos de la línea de comandos (no se utilizan en este caso).
	 */
	public static void main(String[] args) {
		SpringApplication.run(CesurSpringBootApplication.class, args);
	}
}
