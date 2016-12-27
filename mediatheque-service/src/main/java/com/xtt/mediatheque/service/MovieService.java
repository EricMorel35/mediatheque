package com.xtt.mediatheque.service;

import java.util.List;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;

/**
 * Interface de services pour les films.
 *
 * @author Eric Morel
 */
public interface MovieService {

	/**
	 * Méthode retournant la liste exhaustive des films.
	 *
	 * @return La liste de DTO contenant tous les films.
	 */
	List<CatalogItemDTO> getAllMovies() throws TechnicalAccessException, MessageException;

	/**
	 * Méthode récupérant les informations pour un film donné.
	 *
	 * @param movieId
	 *            : l'identifiant du film.
	 * @return Le DTO contenant les données du film.
	 * @throws MovieNotFoundException
	 *             : l'exception est levée si l'identifiant du film ne
	 *             correspond à aucun film.
	 */
	ContentMovieDTO getContentMovie(String movieId)
			throws MovieNotFoundException, TechnicalAccessException, FonctionnalException, MessageException;

	List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException;

	String getCoverByNameFromDisk(String name);

	void persistMovie(String movieName, String userName) throws TechnicalAccessException;

	List<KindsDTO> getKinds() throws TechnicalAccessException;

	List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException;
}
