package org.ufolep.bad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
	info = @Info(
		title="BADUFOLEP REST API",
		version = "r0.0.2",
		description = "API REST de l'application BADUFOLEP (Gestion des championnats départementaux jeunes de badminton organisés par l'UFOLEP)",
		contact = @Contact(
			name = "Eric DECHARTRE",
			email = "eric.dechartre@orange.fr"),
		license = @License(
			name = "Apache 2.0",
			url = "http://www.apache.org/licenses/LICENSE-2.0.html")))
@SpringBootApplication
public class BadUfolepApplication {

	public static void main(String[] args) {
		SpringApplication.run(BadUfolepApplication.class, args);
	}
}
