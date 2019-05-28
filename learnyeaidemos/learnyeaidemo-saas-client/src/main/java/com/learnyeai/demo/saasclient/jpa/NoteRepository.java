package com.learnyeai.demo.saasclient.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learnyeai.demo.resource.saasclient.data.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

	Page<Note> findByNamespaceAndTarget(String namespace, String target, Pageable pageable);
	List<Note> findByNamespaceAndTarget(String namespace, String target);

}
