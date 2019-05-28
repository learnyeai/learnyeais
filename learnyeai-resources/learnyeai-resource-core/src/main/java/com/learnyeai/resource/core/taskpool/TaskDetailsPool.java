package com.learnyeai.resource.core.taskpool;

import java.util.Iterator;

import com.learnyeai.resource.core.details.TaskDetails;

public interface TaskDetailsPool<D extends TaskDetails<T>, T> extends Iterator<TaskDetails<T>> {

}
