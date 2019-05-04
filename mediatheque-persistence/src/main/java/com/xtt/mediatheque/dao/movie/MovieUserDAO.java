package com.xtt.mediatheque.dao.movie;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.model.MovieUserEntity;

@Repository
public interface MovieUserDAO extends PagingAndSortingRepository<MovieUserEntity, Long> {

}
