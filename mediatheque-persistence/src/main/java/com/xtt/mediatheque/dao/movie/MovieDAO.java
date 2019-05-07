package com.xtt.mediatheque.dao.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.model.MovieEntity;

@Repository
public interface MovieDAO extends JpaRepository<MovieEntity, Long> {


}
