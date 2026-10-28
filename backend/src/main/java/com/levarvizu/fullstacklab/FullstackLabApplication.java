package com.levarvizu.fullstacklab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableCaching
@EnableMethodSecurity
public class FullstackLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(FullstackLabApplication.class, args);
	}

	@Bean
	CommandLineRunner debug(Environment env) {

		return args -> {

			System.out.println(
					"================================");

			System.out.println(
					"DB_URL = "
							+ env.getProperty("DB_URL"));

			System.out.println(
					"SPRING_DATASOURCE_URL = "
							+ env.getProperty(
									"spring.datasource.url"));

			System.out.println(
					"================================");

		};

	}

}
