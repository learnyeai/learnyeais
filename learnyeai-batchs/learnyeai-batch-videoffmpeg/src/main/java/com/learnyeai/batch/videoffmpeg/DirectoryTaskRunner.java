package com.learnyeai.batch.videoffmpeg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.learnyeai.batch.taskpool.TaskExecutor;

@Configuration
public class DirectoryTaskRunner implements ApplicationRunner {

	private final ThreadPoolTaskExecutor executor;

	private final TaskExecutor taskExecutor;

	@Autowired
	public DirectoryTaskRunner(ThreadPoolTaskExecutor executor, TaskExecutor taskExecutor) {
		this.executor = executor;
		this.taskExecutor = taskExecutor;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		taskExecutor.execute(executor);
	}

}
