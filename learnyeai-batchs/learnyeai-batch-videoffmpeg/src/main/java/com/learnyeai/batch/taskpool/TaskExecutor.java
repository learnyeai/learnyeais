package com.learnyeai.batch.taskpool;

import java.io.Closeable;
import java.io.IOException;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class TaskExecutor implements Runnable, Closeable {

	private final Task task;
	private TaskListener taskListener;

	public static TaskExecutor of(Task task, TaskListener taskListener) {
		return new TaskExecutor(task, taskListener);
	}

	public static TaskExecutor from(Task task) {
		return new TaskExecutor(task);
	}

	private TaskExecutor(Task task, TaskListener taskListener) {
		this.task = task.taskExecutor(this);
		this.taskListener = taskListener;
	}

	private TaskExecutor(Task task) {
		this(task, null);
	}

	@Override
	public void close() throws IOException {

	}

	private int repeatCurrentCount = 0;
	private int repeatMaxCount = 1;
	private TaskSpeed taskSpeed;
	private TaskProgressListener taskProgressListener;

	@Override
	public void run() {
		try {
			Thread.sleep(15l);
		} catch (InterruptedException e) {
			onThrowable(task, e, repeatCurrentCount);
		}
		try {
			repeatCurrentCount++;
			if (onBefore(task, repeatCurrentCount)) {
				task.run(taskSpeed != null ? taskSpeed : new TaskSpeed() {
					@Override
					public void progress(Object t) {
						onProgress(task, t, repeatCurrentCount);
					}
				}, () -> {
					task.close();
					onClosed(task, repeatCurrentCount);
					if (repeatMaxCount > 0 && repeatCurrentCount >= repeatMaxCount) {
						repeatCurrentCount = 0;
						onAfter(task);
					} else {
						run();
					}
				});
			}
		} catch (Exception ex) {
			onThrowable(task, ex, repeatCurrentCount);
		}

	}

	public TaskListener listener() {
		return taskListener;
	}

	public TaskExecutor listener(TaskListener taskListener) {
		this.taskListener = taskListener;
		return this.progress(taskListener);
	}

	public TaskExecutor progress(TaskProgressListener taskProgressListener) {
		this.taskProgressListener = taskListener;
		return this;
	}

	public TaskProgressListener progress() {
		return taskProgressListener;
	}

	private boolean onBefore(Task taskExecutor, int repeat) {
		if (taskListener != null)
			return taskListener.onBefore(taskExecutor, repeat);
		return true;
	}

	private void onProgress(Task taskExecutor, Object t, int repeat) {
		if (taskProgressListener != null)
			taskProgressListener.onProgress(taskExecutor, t, repeat);
	}

	private void onThrowable(Task taskExecutor, Throwable e, int repeat) {
		if (taskListener != null)
			taskListener.onThrowable(taskExecutor, e, repeat);
	}

	private void onAfter(Task taskExecutor) {
		if (taskListener != null)
			taskListener.onAfter(taskExecutor);
	}

	private void onClosed(Task taskExecutor, int repeat) {
		if (taskListener != null)
			taskListener.onClosed(taskExecutor, repeat);
	}

	public int getRepeatCurrentCount() {
		return repeatCurrentCount;
	}

	public int repeat() {
		return repeatMaxCount;
	}

	public TaskExecutor repeat(int repeatMaxCount) {
		this.repeatMaxCount = repeatMaxCount;
		return this;
	}

	public static void of(TaskSpeed taskSpeed, Task task) {

	}

	public TaskExecutor taskSpeed(TaskSpeed taskSpeed) {
		this.taskSpeed = taskSpeed;
		return this;

	}

	public TaskSpeed getTaskSpeed() {
		return taskSpeed;
	}

	public TaskExecutor parent(Task parent) {
		return listener(parent.taskExecutor().listener()).progress(parent.taskExecutor().progress())
				.repeat(parent.taskExecutor().repeat());
	}

	public static TaskExecutor step(Task parent, TaskSpeed taskSpeed, Closeable closeable, Task task) {
		return from(task).parent(parent).taskSpeed(taskSpeed).close(closeable)
				.executer(parent.taskExecutor().executer());
	}

	private ThreadPoolTaskExecutor executer() {
		return task.executor();
	}

	public TaskExecutor executer(ThreadPoolTaskExecutor executor) {
		task.executor(executor);
		return this;
	}

	public void execute(ThreadPoolTaskExecutor executor, long startTimeout) {
		executer(executor);
		executor.execute(this, startTimeout);
	}

	public void execute(ThreadPoolTaskExecutor executor) {
		executer(executor);
		executor.execute(this);
	}

	public void execute() {
		new Thread(this).start();
	}

	public TaskExecutor close(Closeable closeable) {
		this.taskListener = new TaskListener.Base() {

			@Override
			public void onAfter(Task taskExecutor) {
				try {
					closeable.close();
				} catch (IOException e) {
					onThrowable(task, e, repeatCurrentCount);
				}
			}
		};
		return this;
	}

}
