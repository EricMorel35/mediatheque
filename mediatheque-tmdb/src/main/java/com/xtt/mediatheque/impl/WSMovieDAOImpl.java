package com.xtt.mediatheque.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
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
public class WSMovieDAOImpl extends AbstractDAOImpl implements WSMovieDAO {

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	/**
	 * {@inheritDoc}
	 * 
	 * @throws MessageException
	 */
	@Override
	public MovieItem getContentMovie(final String movieId)
			throws MessageException {
		String url = super.buildMovieUrl(movieId);
		Movie movie = restTemplate.getForObject(url, Movie.class);
		return new MovieWrapped(movie);
	}

	private MoviesList getMovieSearchResults(final String movieName)
			throws MessageException {
		String url = super.buildSearchUrl(movieName);
		MoviesList movies = restTemplate.getForObject(url, MoviesList.class);
		return movies;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws MessageException
	 */
	@Override
	public MovieSearchItem getSearchResultsMovie(final String movieName)
			throws MessageException {
		MoviesList movie = getMovieSearchResults(movieName);
		return new MovieSearchWrapped(movie);
	}

	@Override
	public List<MovieItem> getSearchAllResultsMovie(String movieName)
			throws MessageException {
		List<MovieItem> items = new ArrayList<MovieItem>();
		MoviesList movies = getMovieSearchResults(movieName);
		for (Movie movie : movies.getResults()) {
			items.add(new MovieWrapped(movie));
		}
		return items;
	}

}
