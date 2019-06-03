package com.learnyeai.batch.videoffmpeg;

import java.io.File;
import java.math.BigDecimal;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.learnyeai.batch.taskpool.Task;
import com.learnyeai.batch.taskpool.TaskExecutor;
import com.learnyeai.batch.taskpool.TaskListener;
import com.learnyeai.batch.videoffmpeg.core.DirectoryTaskExecutor;
import com.learnyeai.batch.videoffmpeg.data.ResFileRepository;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class LearnyeaiConfiguration {

	@Autowired
	private ResFileRepository resFileRepository;

	@Autowired
	private ConfigProperties configProperties;

	@Bean
	public ThreadPoolTaskExecutor executor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();
		return executor;
	}

	@Bean
	public TaskExecutor taskExecutor(ThreadPoolTaskExecutor executor) {
		return TaskExecutor.from(new DirectoryTaskExecutor(configProperties.getFfmpegPath(), resFileRepository,
				new File(configProperties.getDir()), configProperties.getUffix())).listener(new TaskListener() {

					BigDecimal count = new BigDecimal(0);

					@Override
					public void onThrowable(Task taskExecutor, Throwable e, int repeat) {
						e.printStackTrace();
						executor.shutdown();
					}

					@Override
					public void onProgress(Task taskExecutor, Object data, int repeat) {
						count = count.add(new BigDecimal(data.toString()));
					}

					@Override
					public void onClosed(Task taskExecutor, int repeat) {
					}

					@Override
					public boolean onBefore(Task taskExecutor, int repeat) {
						System.out.println("开始处理...");
						return true;
					}

					@Override
					public void onAfter(Task taskExecutor) {
						System.out.println(MessageFormat.format("已成功处理{0}条数据", count));
						executor.shutdown();
					}
				});
	}
}
