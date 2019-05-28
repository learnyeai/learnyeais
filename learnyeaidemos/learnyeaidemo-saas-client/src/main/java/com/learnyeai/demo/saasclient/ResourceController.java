package com.learnyeai.demo.saasclient;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@GetMapping(value = "/me")
	public Principal user(Principal principal) {
		return principal;
	}

}
