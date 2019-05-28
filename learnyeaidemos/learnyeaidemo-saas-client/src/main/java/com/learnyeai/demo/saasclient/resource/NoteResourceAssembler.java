package com.learnyeai.demo.saasclient.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.security.core.Authentication;

import com.learnyeai.demo.resource.saasclient.NoteResource;
import com.learnyeai.demo.resource.saasclient.data.Note;
import com.learnyeai.demo.saasclient.controller.NoteContoller;

public class NoteResourceAssembler extends ResourceAssemblerSupport<Note, NoteResource> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Authentication authentication;

	public NoteResourceAssembler(Authentication authentication) {
		super(NoteContoller.class, NoteResource.class);
		this.authentication = authentication;
	}

	@Override
	public NoteResource toResource(Note note) {
		NoteResource resource = new NoteResource();

		resource.id = note.getId();
		resource.content = note.getContent();
		resource.scene = note.getScene();

		resource.add(linkTo(methodOn(NoteContoller.class).findById(authentication, note.getId())).withRel("get"));
		resource.add(linkTo(methodOn(NoteContoller.class).put(authentication, note.getId(), note)).withRel("put"));
		try {
			resource.add(linkTo(methodOn(NoteContoller.class).patch(authentication, note.getId(), note)).withRel("patch"));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		resource.add(linkTo(methodOn(NoteContoller.class).destroy(authentication, note.getId())).withRel("delete"));
		return resource;
	}

}
