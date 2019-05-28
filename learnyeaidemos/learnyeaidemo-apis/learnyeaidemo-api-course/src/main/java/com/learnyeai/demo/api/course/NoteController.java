package com.learnyeai.demo.api.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnyeai.demo.resource.saasclient.NoteResource;
import com.learnyeai.demo.resource.saasclient.data.Note;
import com.learnyeai.demo.sdk.saas.client.LearnyeaiDemoSaaSClientApiNote;
import com.netflix.client.http.HttpHeaders;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

	private LearnyeaiDemoSaaSClientApiNote client;

	@Autowired
	public NoteController(LearnyeaiDemoSaaSClientApiNote client) {
		super();
		this.client = client;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public ResponseEntity<PagedResources<NoteResource>> findAll(Pageable pageable,
			@RequestHeader("Authorization") String authorization) {
		return client.findAll(pageable, authorization);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<NoteResource>> findAll(@RequestHeader("Authorization") String authorization) {
		return client.findAll(authorization);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<NoteResource> findById(@PathVariable("id") String id,
			@RequestHeader("Authorization") String authorization) {
		return client.findById(id, authorization);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<NoteResource> create(@RequestBody Note note,
			@RequestHeader("Authorization") String authorization) {
		return client.create(note, authorization);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<NoteResource> put(@PathVariable("id") String id, @RequestBody Note note,
			@RequestHeader("Authorization") String authorization) {
		return client.put(id, note, authorization);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public ResponseEntity<NoteResource> patch(@PathVariable("id") String id, @RequestBody Note note,
			@RequestParam("access_token") String accessToken) {
		return client.patch(id, note, accessToken);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> destroy(@PathVariable String id, @RequestHeader HttpHeaders headers,
			@RequestParam("access_token") String accessToken) {
		return client.destroy(id, accessToken);
	}

}
