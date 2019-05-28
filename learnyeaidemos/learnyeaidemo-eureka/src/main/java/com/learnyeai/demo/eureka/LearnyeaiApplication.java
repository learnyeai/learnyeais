package com.learnyeai.demo.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LearnyeaiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}
}
