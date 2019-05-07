package com.xtt.mediatheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity.DirectorsEmbeddableEntity;

public interface DirectorDAO extends JpaRepository<MovieDirectorsEntity, DirectorsEmbeddableEntity> {

}
