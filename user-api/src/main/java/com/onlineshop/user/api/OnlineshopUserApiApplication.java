package com.onlineshop.user.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.onlineshop.user.api.repository")
public class OnlineshopUserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineshopUserApiApplication.class, args);
	}

}
