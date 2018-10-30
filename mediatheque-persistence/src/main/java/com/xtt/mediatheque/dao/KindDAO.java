package com.xtt.mediatheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieKindsEntity.KindsEmbeddableEntity;

@Repository
public interface KindDAO extends JpaRepository<MovieKindsEntity, KindsEmbeddableEntity> {

}
