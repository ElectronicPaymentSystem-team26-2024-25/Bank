package com.example.BankSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankSystemApplication {
	private static final Logger log = LoggerFactory.getLogger(BankSystemApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BankSystemApplication.class, args);
	}

}
