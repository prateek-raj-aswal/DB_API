package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})

public class SpringBootHelloWorldApplication {
	public static void main(String[] args) {
		System.out.println("inside spring class");
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
		System.out.println("outside spring class");
	}
}
