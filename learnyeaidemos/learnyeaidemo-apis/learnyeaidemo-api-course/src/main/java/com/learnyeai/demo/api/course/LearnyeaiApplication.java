package com.learnyeai.demo.api.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.learnyeai.demo.sdk.saas.client.NoteFeignClient;
import com.learnyeai.demo.sdk.saas.client.SDKSaaSClientDemo;

import cn.jovanyfreamwork.cloud.saas.core.OauthRibbonClient;
import cn.jovanyfreamwork.cloud.saas.core.SceneFeignClient;
import cn.jovanyfreamwork.cloud.saas.sdk.SDKSaaS;

@SpringBootApplication
@SDKSaaSClientDemo
@SDKSaaS
@EnableFeignClients(clients = { NoteFeignClient.class, SceneFeignClient.class })
@EntityScan("com.learnyeai.demo.api.course.data")
public class LearnyeaiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public OauthRibbonClient oauthRibbonClient(RestTemplate restTemplate) {
		return new OauthRibbonClient(restTemplate, "91f25b548c8b2711aaf78ce3126fe61e");
	}

}
