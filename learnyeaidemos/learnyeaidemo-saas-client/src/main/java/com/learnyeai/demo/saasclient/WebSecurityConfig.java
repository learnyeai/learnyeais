package com.learnyeai.demo.saasclient;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	Set<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> configurerAdapters;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		for (SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> configurer : configurerAdapters) {
			http.apply(configurer);
		}
		super.configure(http);
		http.csrf().disable();
	}

}
