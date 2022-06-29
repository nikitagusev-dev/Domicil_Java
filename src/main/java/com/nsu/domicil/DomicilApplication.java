package com.nsu.domicil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DomicilApplication {
	public static void main(String[] args) {
		SpringApplication.run(DomicilApplication.class, args);
	}
}
