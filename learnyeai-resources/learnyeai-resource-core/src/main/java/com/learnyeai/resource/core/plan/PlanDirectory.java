package com.learnyeai.resource.core.plan;

import com.learnyeai.resource.core.details.TeachableDetails;

public interface PlanDirectory<TD extends TeachableDetails> {

	String getName();

	TeachingTaskPlan<TD> getTeachingTaskPlan();

}
