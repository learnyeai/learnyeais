package com.learnyeai.demo.saas;

import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@BasePathAwareController
public class ResourceController {

	@GetMapping(value = "/me")
	@ResponseBody
	public Authentication user(Authentication authentication) {
		return authentication;
	}

}
