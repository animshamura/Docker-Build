package com.javatechie.spring.boot.docker.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootDockerApplication {

    @GetMapping("/")
	public String showRunning() {
		return "<h1><center>Springboot app is running...</center></h1>";
	}
	@GetMapping("/greet")
	public String getMessage() {
		return "<h1><center>Springboot app is greeting...</center></h1>";
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDockerApplication.class, args);
	}

}
