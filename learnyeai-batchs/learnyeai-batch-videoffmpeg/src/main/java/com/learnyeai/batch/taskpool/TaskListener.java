package com.learnyeai.batch.taskpool;

public interface TaskListener extends TaskProgressListener {

	public class Base implements TaskListener {

		
		@Override
		public boolean onBefore(Task taskExecutor, int repeat) {
			return true;
		}

		@Override
		public void onProgress(Task taskExecutor, Object data, int repeat) {

		}

		@Override
		public void onThrowable(Task taskExecutor, Throwable e, int repeat) {

		}

		@Override
		public void onAfter(Task taskExecutor) {

		}

		@Override
		public void onClosed(Task taskExecutor, int repeat) {

		}

	}

	boolean onBefore(Task taskExecutor, int repeat);

	void onThrowable(Task taskExecutor, Throwable e, int repeat);

	void onAfter(Task taskExecutor);

	void onClosed(Task taskExecutor, int repeat);

}
