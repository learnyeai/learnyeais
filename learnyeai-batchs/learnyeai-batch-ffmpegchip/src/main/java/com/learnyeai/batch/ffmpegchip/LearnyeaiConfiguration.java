package com.learnyeai.batch.ffmpegchip;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.jovany.command.CommandApi;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class LearnyeaiConfiguration {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ConfigProperties configProperties;

	@Bean
	public CommandApi ffmpeg() {
		return new CommandApi(new File(configProperties.getFfmpegPath()))
				.setError(t -> logger.error(t.getMessage(), t));
	}

}
