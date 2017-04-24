package com.xtt.mediatheque;

import java.util.List;

import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;

public interface WSMovieDAO {

	MovieItem getContentMovie(long movieId);

	MovieSearchItem getSearchResultsMovie(String movieName);

	List<MovieItem> getSearchAllResultsMovie(String movieName);
}
