package com.xtt.mediatheque.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.model.Movie;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MoviesList;
import com.xtt.mediatheque.wrapped.MovieSearchWrapped;
import com.xtt.mediatheque.wrapped.MovieWrapped;

@Repository
public class WSMovieDAOImpl implements WSMovieDAO {

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

	/**
	 * {@inheritDoc}
	 *
	 * @throws MessageException
	 */
	@Override
	public MovieItem getContentMovie(final String movieId) {
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("movie", movieId);
		ResponseEntity<Movie> movie = restTemplate.getForEntity(movieUrl, Movie.class);
		return new MovieWrapped(movie.getBody());
	}

	private MoviesList getMovieSearchResults(final String movieName) {
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("key", apiKey);
		uriParams.put("query", movieName);
		uriParams.put("language", "fr");

		MoviesList movies = null;
		try {
			movies = restTemplate.getForObject(searchUrl, MoviesList.class, uriParams);
		} catch (RestClientException e) {
			System.out.println(movieName);
			System.out.println(e);
		}

		return movies;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws MessageException
	 */
	@Override
	public MovieSearchItem getSearchResultsMovie(final String movieName) {
		MoviesList movie = getMovieSearchResults(movieName);
		return new MovieSearchWrapped(movie);
	}

	@Override
	public List<MovieItem> getSearchAllResultsMovie(String movieName) {
		List<MovieItem> items = new ArrayList<MovieItem>();
		MoviesList movies = getMovieSearchResults(movieName);
		for (Movie movie : movies.getResults()) {
			items.add(new MovieWrapped(movie));
		}
		return items;
	}

}
