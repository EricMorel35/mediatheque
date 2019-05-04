package com.xtt.mediatheque.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.model.MovieSearchItem;

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
	Page<CatalogItemDTO> getAllMovies(Pageable pageable);

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
	 * @param movie : the movie to save.
	 */
	void saveMovie(MovieSearchItem movie);

//	List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException;

	List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException;
}
