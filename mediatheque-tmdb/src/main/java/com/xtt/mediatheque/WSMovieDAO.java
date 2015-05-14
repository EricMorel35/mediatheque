package com.xtt.mediatheque;

import java.util.List;

import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;

public interface WSMovieDAO {

	MovieItem getContentMovie(String movieId) throws MessageException;

	MovieSearchItem getSearchResultsMovie(String movieName) throws MessageException;
	
	List<MovieItem> getSearchAllResultsMovie(String movieName) throws MessageException;
}
