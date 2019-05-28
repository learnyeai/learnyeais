package com.learnyeai.resource.core.taskpool;

import com.learnyeai.resource.core.details.TeachableDetails;
import com.learnyeai.resource.core.task.TeachingTaskDetails;

public interface TeachingTaskPool<TD extends TeachableDetails, TTD extends TeachingTaskDetails<TD>>
		extends TaskDetailsPool<TTD, TD> {

}
