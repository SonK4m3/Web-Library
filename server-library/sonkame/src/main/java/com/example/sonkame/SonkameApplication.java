package com.example.sonkame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controllers","daos","models"})
public class SonkameApplication {

	public static void main(String[] args) {
		SpringApplication.run(SonkameApplication.class, args);
	}

}
