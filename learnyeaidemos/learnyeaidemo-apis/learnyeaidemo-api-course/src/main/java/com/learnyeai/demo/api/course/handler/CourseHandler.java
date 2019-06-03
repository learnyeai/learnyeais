package com.learnyeai.demo.api.course.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.learnyeai.demo.api.course.data.Course;
import com.learnyeai.demo.api.course.jpa.CourseRepository;

import cn.jovanyfreamwork.cloud.saas.core.OauthRibbonClient;
import cn.jovanyfreamwork.cloud.saas.core.SceneFeignClient;
import cn.jovanyfreamwork.cloud.saas.data.Scene;
import cn.jovanyfreamwork.cloud.saas.data.SceneID;

@RepositoryEventHandler
public class CourseHandler {

	@Autowired
	OauthRibbonClient oauthRibbonClient;

	@Autowired
	SceneFeignClient sceneFeignClient;

	@Autowired
	CourseRepository courseRepository;

	@HandleAfterSave
	public void handleCourseSave(Course c) throws JsonParseException, JsonMappingException, IOException {
		if (c.getSaasKey() != null) {
			return;
		}
		SceneID sceneID = new SceneID("course", c.getId());
		Scene scene = sceneFeignClient.save(new Scene(sceneID)).getBody().getContent();
		String saasKey = oauthRibbonClient.token(scene.getId().toString());
		c.setSaasKey(saasKey);
		courseRepository.save(c);
	}

}
