package com.learnyeai.demo.saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import cn.jovanyfreamwork.cloud.saas.EnableSaaS;

@SpringBootApplication
@EnableSaaS
@EnableDiscoveryClient
public class LearnyeaiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}
}
