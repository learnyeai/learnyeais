package com.learnyeai.batch.videoffmpeg.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResFileRepository extends JpaRepository<ResFile, String> {

	ResFile findTopByFileName(String name);

}
