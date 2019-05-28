package com.learnyeai.demo.sdk.saas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.learnyeai.demo.resource.saasclient.NoteResource;

@FeignClient("learnyeai-demo-saas-client")
public interface LearnyeaiDemoSaaSClientApi {

	@RequestMapping("/notes")
	public interface Note extends LearnyeaiDemoSaaSClientApi {

		@RequestMapping(method = RequestMethod.GET, value = "/search")
		ResponseEntity<PagedResources<NoteResource>> findAll(Pageable pageable);

		@RequestMapping(method = RequestMethod.GET)
		ResponseEntity<Resources<NoteResource>> findAll();

		@RequestMapping(method = RequestMethod.GET, value = "/{id}")
		ResponseEntity<NoteResource> findById(@PathVariable String id);

		@RequestMapping(method = RequestMethod.POST)
		ResponseEntity<NoteResource> create(@RequestBody Note note);

		@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
		ResponseEntity<NoteResource> put(@PathVariable String id, @RequestBody Note note);

		@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
		ResponseEntity<NoteResource> patch(@PathVariable String id, @RequestBody Note note);

		@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
		ResponseEntity<?> destroy(@PathVariable String id);

	}

}
