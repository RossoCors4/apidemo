package com.tintin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@OpenAPIDefinition(
	info = @Info(
		title = "Demo Employee API",
		version = "1.0",
		description = "API documentation for employee management"
	)
)
@RestController
@RequestMapping("/")
@SpringBootApplication
public class DemoApplication {
	@GetMapping
	String home(){
		return "Demo Employee API";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
