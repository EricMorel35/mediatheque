package com.xtt.mediatheque.service;

import java.util.List;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;

/**
 * Service interface for movies.
 *
 * @author Eric Morel
 */
public interface MovieService {

	/**
	 * Finds all movies.
	 *
	 * @return a DTO list which contains movies.
	 */
	List<CatalogItemDTO> getAllMovies();

	/**
	 * Gets metadata such as title, synopsis, actors, kind of movie for a given
	 * movie.
	 *
	 * @param movieId : the id of movie.
	 * @return The DTO object which contains movie's metadata.
	 * @throws MovieNotFoundException : This exception is thrown if no movie is
	 *                                found for the given id.
	 */
	ContentMovieDTO getContentMovie(long movieId) throws MovieNotFoundException;

	/**
	 * Save current movie.
	 * 
	 * @param movieName : the movie to save.
	 */
	void saveMovie(String movieName);
//
//	List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException;
//
//	String getCoverByNameFromDisk(String name);
//
//	List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException;
}
