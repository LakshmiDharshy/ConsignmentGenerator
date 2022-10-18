package com.consign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan({ "com.*" })

@SpringBootApplication
public class NoteNumberGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteNumberGeneratorApplication.class, args);
	}

}
