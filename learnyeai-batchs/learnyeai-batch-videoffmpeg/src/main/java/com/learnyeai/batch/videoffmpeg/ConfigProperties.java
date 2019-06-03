package com.learnyeai.batch.videoffmpeg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "batch")
@Component
public class ConfigProperties {

	private String dir;

	private String ffmpegPath;

	private String[] uffix = new String[] { "mp4" };

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}

	public void setFfmpegPath(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}

	public String[] getUffix() {
		return uffix;
	}

	public void setUffix(String[] uffix) {
		this.uffix = uffix;
	}

}
