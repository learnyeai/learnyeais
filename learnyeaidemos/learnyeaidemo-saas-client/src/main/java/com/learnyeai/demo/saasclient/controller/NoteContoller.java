package com.learnyeai.demo.saasclient.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learnyeai.demo.resource.saasclient.NoteResource;
import com.learnyeai.demo.resource.saasclient.data.Note;
import com.learnyeai.demo.saasclient.jpa.NoteRepository;
import com.learnyeai.demo.saasclient.resource.NoteResourceAssembler;

import cn.jovanyframework.cloud.saasclient.resource.ScenePrincipal;

@RestController
@RequestMapping("/notes")
public class NoteContoller {

	private final NoteRepository repository;

	@Autowired
	public NoteContoller(NoteRepository repo) {
		this.repository = repo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/ping")
	public Authentication ping(Authentication authentication) {
		return authentication;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search")
	public ResponseEntity<?> findAll(Authentication authentication, Pageable pageable) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		Page<Note> page = repository.findByNamespaceAndTarget(scene.getNamespace(), scene.getTarget(), pageable);
		PageMetadata pageMetadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		PagedResources<NoteResource> resources = new PagedResources<NoteResource>(
				assembler.toResources(page.getContent()), pageMetadata);
		resources.add(linkTo(methodOn(NoteContoller.class).findAll(authentication, pageable)).withSelfRel());
		return ResponseEntity.ok().body(resources);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll(Authentication authentication) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		List<Note> list = repository.findByNamespaceAndTarget(scene.getNamespace(), scene.getTarget());
		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		Resources<NoteResource> resources = new Resources<NoteResource>(assembler.toResources(list));
		resources.add(linkTo(methodOn(NoteContoller.class).findAll(authentication)).withSelfRel());
		return ResponseEntity.ok().body(resources);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findById(Authentication authentication, @PathVariable String id) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		Optional<Note> result = repository.findById(id);
		if (!result.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Note persistentNote = result.get();
		if (!persistentNote.getScene().equals(scene)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		NoteResource resource = assembler.toResource(repository.save(persistentNote));
		resource.add(linkTo(methodOn(NoteContoller.class).findById(authentication, id)).withSelfRel());
		return ResponseEntity.ok().body(resource);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(Authentication authentication, @RequestBody Note note) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		note.setNamespace(scene.getNamespace());
		note.setTarget(scene.getTarget());
		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		NoteResource resource = assembler.toResource(repository.save(note));
		resource.add(linkTo(methodOn(NoteContoller.class).create(authentication, note)).withSelfRel());
		return ResponseEntity.ok().body(resource);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> put(Authentication authentication, @PathVariable String id, @RequestBody Note note) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		Optional<Note> result = repository.findById(id);
		if (!result.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Note persistentNote = result.get();
		if (!persistentNote.getScene().equals(scene)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		note.setId(persistentNote.getId());
		note.setNamespace(persistentNote.getNamespace());
		note.setTarget(persistentNote.getTarget());
		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		NoteResource resource = assembler.toResource(repository.save(note));
		resource.add(linkTo(methodOn(NoteContoller.class).put(authentication, id, note)).withSelfRel());
		return ResponseEntity.ok().body(note);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
	public ResponseEntity<?> patch(Authentication authentication, @PathVariable String id, @RequestBody Note note)
			throws IllegalArgumentException, IllegalAccessException {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		Optional<Note> result = repository.findById(id);
		if (!result.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Note persistentNote = result.get();
		if (!persistentNote.getScene().equals(scene)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		Field[] fieldArr = Note.class.getDeclaredFields();
		for (Field field : fieldArr) {
			switch (field.getName()) {
			case "id":
			case "namespace":
			case "target":
				break;
			default:
				try {
					if (field.get(note) != null) {
						field.set(persistentNote, field.get(note));
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw e;
				}
				break;
			}
		}

		NoteResourceAssembler assembler = new NoteResourceAssembler(authentication);
		NoteResource resource = assembler.toResource(persistentNote);
		resource.add(linkTo(methodOn(NoteContoller.class).patch(authentication, id, note)).withSelfRel());
		return ResponseEntity.ok().body(note);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> destroy(Authentication authentication, @PathVariable String id) {
		ScenePrincipal scene = (ScenePrincipal) authentication.getPrincipal();
		Optional<Note> result = repository.findById(id);
		if (!result.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		Note persistentNote = result.get();
		if (!persistentNote.getScene().equals(scene)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		repository.delete(persistentNote);
		return ResponseEntity.ok().build();
	}

}
