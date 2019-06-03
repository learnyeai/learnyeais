package com.learnyeai.batch.taskpool;

public interface TaskProgressListener {

	void onProgress(Task taskExecutor, Object data, int repeat);

}
