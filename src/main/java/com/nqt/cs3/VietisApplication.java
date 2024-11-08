package com.nqt.cs3;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class VietisApplication {

	public static void main(String[] args) {
		SpringApplication.run(VietisApplication.class, args);
	}

}
