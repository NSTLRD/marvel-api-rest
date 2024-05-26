package com.marvel.restapi1.Marvel_API_Rest_v1;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.marvel.restapi1", "com.marvel.marvelapiconsumer.service"})
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
		title = "API-REST-REGISTER",
		version = "1.0",
		description = "Securely manage user registrations and authentications. Provides JSON Web Tokens (JWT) on registration and login, which are required for subsequent authenticated requests."
))
public class MarvelApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelApiRestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
