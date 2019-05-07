package com.xtt.mediatheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieActorsEntity.ActorsEmbeddableEntity;

public interface ActorDAO extends JpaRepository<MovieActorsEntity, ActorsEmbeddableEntity> {

}
