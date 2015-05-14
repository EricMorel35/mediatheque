package com.xtt.mediatheque.dao;

import java.util.List;

import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public interface PersistenceDAO {

	List<MovieUserEntityItem> getAllMovies();

	String getCoverByNameFromDisk(String name);

	MovieUserEntityItem getMovieById(Integer movieId)
			throws TechnicalAccessException;

	void updateDatasMovie(MovieUserEntityItem item, MovieSearchItem movieItem);

	void updateFullDatas(MovieUserEntityItem item, MovieItem movieItem)
			throws TechnicalAccessException;

	void updateIdAllocine(MovieUserEntityItem item);
	
	void persistMovie(String movieName, String userName) throws TechnicalAccessException;
	
	List<KindItem> getKinds() throws TechnicalAccessException;
	
	List<MovieUserEntityItem> getMoviesByKind(String kind) throws TechnicalAccessException;

}
