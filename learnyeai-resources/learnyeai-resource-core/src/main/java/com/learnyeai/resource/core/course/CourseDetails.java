package com.learnyeai.resource.core.course;

import java.util.List;

import com.learnyeai.resource.core.details.TeachableDetails;
import com.learnyeai.resource.core.task.TeachingTaskDetails;
import com.learnyeai.resource.core.taskpool.TeachingTaskPool;

public interface CourseDetails {

	List<ChapterDetails> getChapters();
	
	TeachingTaskPool<TeachableDetails, TeachingTaskDetails<TeachableDetails>> getTeachingTasks();
	
}
