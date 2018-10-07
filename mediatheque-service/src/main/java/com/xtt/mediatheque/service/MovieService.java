package com.xtt.mediatheque.service;

import java.util.List;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
//import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
//import com.xtt.mediatheque.model.MovieEntity;
//import com.xtt.mediatheque.model.MovieUserEntity;

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
	List<CatalogItemDTO> getAllMovies() throws TechnicalAccessException, MessageException;

	/**
	 * Méthode récupérant les informations pour un film donné.
	 *
	 * @param movieId : l'identifiant du film.
	 * @return Le DTO contenant les données du film.
	 * @throws MovieNotFoundException : l'exception est levée si l'identifiant du
	 *                                film ne correspond à aucun film.
	 */
	ContentMovieDTO getContentMovie(long movieId)
			throws MovieNotFoundException, TechnicalAccessException, MessageException;

//	void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException;
//
//	void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException;
//
//	List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException;
//
//	String getCoverByNameFromDisk(String name);
//
//	void persistMovie(String movieName, String userName) throws TechnicalAccessException;
//
//	List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException;
}
