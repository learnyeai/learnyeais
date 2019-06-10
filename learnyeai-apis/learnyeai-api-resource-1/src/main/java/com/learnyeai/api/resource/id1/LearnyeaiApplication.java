package com.learnyeai.api.resource.id1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.jovanyfreamwork.cloud.saasclient.SaasClient;
import cn.jovanyfreamwork.cloud.saasclient.User;

@SpringBootApplication
@SaasClient(user = @User({ "1", "2" }))
public class LearnyeaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}

}
