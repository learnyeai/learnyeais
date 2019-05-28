package com.learnyeai.demo.api.course.handler;

import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.learnyeai.demo.api.course.data.Course;

@RepositoryEventHandler
public class CourseHandler {

	@HandleBeforeSave
	public void handleCourseSave(Course c) {
		if (c.getSaasKey() != null) {
			return;
		}
		
	}

}
