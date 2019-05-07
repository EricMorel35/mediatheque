package com.xtt.mediatheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieCountryEntity.CountryEmbeddableEntity;

public interface CountryDAO extends JpaRepository<MovieCountryEntity, CountryEmbeddableEntity> {

}
