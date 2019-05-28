package com.learnyeai.demo.sdk.saas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.learnyeai.demo.resource.saasclient.NoteResource;
import com.learnyeai.demo.resource.saasclient.data.Note;

@FeignClient(name = "learnyeai-demo-saas-client", path = "/learnyeai/demo/saasclient")
@RequestMapping("/notes")
public interface LearnyeaiDemoSaaSClientApiNote {

	@RequestMapping(method = RequestMethod.GET, value = "/ping")
	String ping(@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	ResponseEntity<PagedResources<NoteResource>> findAll(Pageable pageable,
			@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.GET, value = "/")
	ResponseEntity<Resources<NoteResource>> findAll(@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<NoteResource> findById(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<NoteResource> create(@RequestBody Note note, @RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	ResponseEntity<NoteResource> put(@PathVariable("id") String id, @RequestBody Note note,
			@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	ResponseEntity<NoteResource> patch(@PathVariable("id") String id, @RequestBody Note note,
			@RequestHeader("Authorization") String authorization);

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	ResponseEntity<?> destroy(@PathVariable("id") String id, @RequestHeader("Authorization") String authorization);

}
