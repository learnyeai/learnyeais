package com.learnyeai.app.vodserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.jovanyframework.cloud.filestore.EnableVODServer;

@SpringBootApplication
@EnableVODServer
public class LearnyeaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnyeaiApplication.class, args);
	}

}
