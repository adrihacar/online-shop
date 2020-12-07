package com.onlineshop.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.onlineshop.product.api.repository")
public class OnlineshopProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineshopProductApiApplication.class, args);
	}

}
