package com.learnyeai.resource.core.course;

import java.util.List;

import com.learnyeai.resource.core.details.TeachableDetails;
import com.learnyeai.resource.core.plan.TeachingPlan;
import com.learnyeai.resource.core.plan.TeachingPlanPool;
import com.learnyeai.resource.core.taskpool.TeachingTaskPool;

public interface CourseDetails<TD extends TeachableDetails> {

	String getName();

	List<ChapterDetails> getChapters();

	TeachingTaskPool<TD> getTeachingTasks();

	TeachingPlan<TD> getDefaultTeachingPlan();

	TeachingPlanPool<TD> getTeachingPlans();

}
