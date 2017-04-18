package com.xtt.mediatheque.dao.movie;

import java.util.List;

import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public interface MovieDAO {

	List<MovieUserEntityItem> getAllMovies() throws TechnicalAccessException;

	void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException;

	void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException;

	MovieUserEntityItem getMovieByExternalId(long movieId) throws TechnicalAccessException;

	void updateFullDatas(MovieUserEntityItem item, MovieItem movieItem) throws TechnicalAccessException;

}
