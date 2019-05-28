package com.learnyeai.resource.core.task;

import com.learnyeai.resource.core.details.TaskDetails;
import com.learnyeai.resource.core.details.TeachableDetails;

public interface TeachingTaskDetails<T extends TeachableDetails> extends TaskDetails<T> {

    String getName();

}
