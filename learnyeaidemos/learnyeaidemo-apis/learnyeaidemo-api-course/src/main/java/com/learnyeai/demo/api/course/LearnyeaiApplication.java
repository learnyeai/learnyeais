package com.learnyeai.demo.api.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

import com.learnyeai.demo.sdk.saas.client.SDKSaaSClient;

@SpringBootConfiguration
@SDKSaaSClient
public class LearnyeaiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}
}
