package com.example.adminServer;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminServerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAdminServerAppApplication.class, args);
	}

}
