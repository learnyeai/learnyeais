package com.learnyeai.resource.core.plan;

import com.learnyeai.resource.core.details.TeachableDetails;
import com.learnyeai.resource.core.task.TeachingTaskDetails;

public interface TeachingTaskPlan<TD extends TeachableDetails> {

	TeachingTaskDetails<TD> getTeachingTaskDetails();

}
