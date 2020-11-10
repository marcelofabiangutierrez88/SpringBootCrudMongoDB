package com.marcelo.spring.data.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.marcelo.spring.data.mongodb.model.Tutorial;


public interface TutorialRepository extends MongoRepository<Tutorial, String> {
	  List<Tutorial> findByTitleContaining(String title);
	  List<Tutorial> findByPublished(boolean published);
	}