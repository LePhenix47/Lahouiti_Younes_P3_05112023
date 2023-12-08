package com.openclassrooms.p3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.openclassrooms.p3")
public class P3Application {

	public static void main(String[] args) {
		SpringApplication.run(P3Application.class, args);
	}

}
