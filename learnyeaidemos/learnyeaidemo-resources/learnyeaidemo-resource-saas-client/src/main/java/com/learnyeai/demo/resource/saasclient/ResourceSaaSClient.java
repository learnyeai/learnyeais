package com.learnyeai.demo.resource.saasclient;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EntityScan(basePackages = "com.learnyeai.demo.resource.saasclient")
public @interface ResourceSaaSClient {

	String GENERATOR_JPA_UUID = "jpa-uuid";
}
