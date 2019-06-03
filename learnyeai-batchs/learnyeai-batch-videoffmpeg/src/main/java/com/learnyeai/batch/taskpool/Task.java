package com.learnyeai.batch.taskpool;

import java.io.Closeable;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public abstract class Task implements Closeable {

	abstract public void run(TaskSpeed taskSpeed, Closeable closeable);

	private TaskExecutor taskExecutor;

	private ThreadPoolTaskExecutor executor;

	public Task taskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
		return this;
	}

	public TaskExecutor taskExecutor() {
		return this.taskExecutor;
	}

	public Task executor(ThreadPoolTaskExecutor executor) {
		this.executor = executor;
		return this;
	}

	public ThreadPoolTaskExecutor executor() {
		return this.executor;
	}
	
	public void error(Throwable t) {
		taskExecutor.listener().onThrowable(this, t, taskExecutor.getRepeatCurrentCount());
	}

}
