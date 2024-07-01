package com.dev.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API documentation.",
				description = "MicroBank Accounts microservice REST API documentation",
				version = "v1",
				contact = @Contact(
						name = "Sachin Tripathi",
						email = "iamatwork@gmail.com",
						url = "https://www.google.com/"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.google.com/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "MicroBank Accounts REST Service Open Documentation",
				url = "https://www.google.com/"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
