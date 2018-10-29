package com.xtt.mediatheque.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.model.Movie;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MoviesList;
import com.xtt.mediatheque.wrapped.MovieSearchWrapped;
import com.xtt.mediatheque.wrapped.MovieWrapped;


@Repository
public class WSMovieDAOImpl implements WSMovieDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(WSMovieDAOImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	private String searchUrl;
	private String movieUrl;
	private String apiKey;

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public void setMovieUrl(String movieUrl) {
		this.movieUrl = movieUrl;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtt.mediatheque.WSMovieDAO#getContentMovie(long)
	 */
	@Override
	public MovieItem getContentMovie(final long movieId) {
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("movie", String.valueOf(movieId));
		ResponseEntity<Movie> movie = restTemplate.getForEntity(movieUrl, Movie.class, movieId);
		return new MovieWrapped(movie.getBody());
	}

	private MoviesList getMovieSearchResults(final String movieName) {
		Map<String, String> uriParams = new HashMap<>();
		uriParams.put("key", apiKey);
		uriParams.put("query", movieName);
		uriParams.put("language", "fr");

		MoviesList movies = new MoviesList();
		try {
			movies = restTemplate.getForObject(searchUrl, MoviesList.class, uriParams);
		} catch (RestClientException e) {
			LOG.error(new StringBuilder("[Error] - Error when call MovieDB API ").append(movieName).toString());
		}

		return movies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtt.mediatheque.WSMovieDAO#getSearchResultsMovie(java.lang.String)
	 */
	@Override
	public MovieSearchItem getSearchResultsMovie(final String movieName) {
		MoviesList movie = getMovieSearchResults(movieName);
		return new MovieSearchWrapped(movie);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtt.mediatheque.WSMovieDAO#getSearchAllResultsMovie(java.lang.String)
	 */
	@Override
	public List<MovieItem> getSearchAllResultsMovie(String movieName) {
		List<MovieItem> items = new ArrayList<>();
		MoviesList movies = getMovieSearchResults(movieName);
		for (Movie movie : movies.getResults()) {
			items.add(new MovieWrapped(movie));
		}

		return items;
	}

}
