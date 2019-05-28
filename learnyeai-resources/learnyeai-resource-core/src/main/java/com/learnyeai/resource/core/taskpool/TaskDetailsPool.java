package com.learnyeai.resource.core.taskpool;

import com.learnyeai.resource.core.details.TaskDetails;

public interface TaskDetailsPool<Details extends TaskDetails<Task>, Task> extends Iterable<TaskDetails<Task>> {

}
