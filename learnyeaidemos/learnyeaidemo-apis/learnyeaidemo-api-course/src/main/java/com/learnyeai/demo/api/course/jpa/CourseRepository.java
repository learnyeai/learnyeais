package com.learnyeai.demo.api.course.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.learnyeai.demo.api.course.data.Course;

@Repository
@RepositoryRestResource(path = "courses", itemResourceRel = "course")
public interface CourseRepository extends JpaRepository<Course, String> {

}
