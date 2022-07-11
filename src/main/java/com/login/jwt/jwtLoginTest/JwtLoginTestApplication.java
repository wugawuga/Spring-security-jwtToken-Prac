package com.login.jwt.jwtLoginTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JwtLoginTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtLoginTestApplication.class, args);
	}

}
