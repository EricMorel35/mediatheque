package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dao.PersistenceDAO;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;
import com.xtt.mediatheque.service.MovieService;

/**
 * Implémentation de {@link MovieService}
 *
 * @author Eric Morel
 */
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private WSMovieDAO wsMovieDAO;

	@Autowired
	private MovieDTOFactory dtoFactory;

	@Autowired
	private PersistenceDAO persistenceDAO;

	@Autowired
	private MessageUtils messages;

	@Autowired
	private MovieDAO movieDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CatalogItemDTO> getAllMovies() throws MessageException, TechnicalAccessException {
		return loadAllMovies();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.mediatheque.xtt.service.MovieService#getContentMovie(java.lang.String
	 * )
	 */
	@Override
	public ContentMovieDTO getContentMovie(final String movieId)
			throws MovieNotFoundException, TechnicalAccessException, FonctionnalException, MessageException {

		// int movie = 0;
		// try {
		// movie = Integer.valueOf(movieId);
		// } catch (NumberFormatException e) {
		// throw new
		// FonctionnalException(messages.getMessage(MediathequeConstants.MOVIE_NUMBER));
		// }

		// MovieUserEntityItem movieEntity = persistenceDAO.getMovieById(movie);
		MovieUserEntityItem movieEntity = movieDAO.getMovieById(Integer.valueOf(movieId));
		if (movieEntity != null) {
			if (StringUtils.isEmpty(movieEntity.getReleaseYear()) || StringUtils.isEmpty(movieEntity.getSynopsis())) {
				MovieItem movieItem = wsMovieDAO.getContentMovie(movieId);
				persistenceDAO.updateFullDatas(movieEntity, movieItem);
			}

			return dtoFactory.buildFullMovieDTO(movieEntity);

		} else {
			throw new MovieNotFoundException(
					messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND, new String[] { movieId }));
		}
	}

	@Override
	public String getCoverByNameFromDisk(final String name) {
		// return persistenceDAO.getCoverByNameFromDisk(name);
		return "";
	}

	private List<CatalogItemDTO> loadAllMovies() throws MessageException, TechnicalAccessException {
		List<CatalogItemDTO> listMoviesDTO = new ArrayList<CatalogItemDTO>();
		List<MovieUserEntityItem> moviesList = persistenceDAO.getAllMovies();
		for (MovieUserEntityItem item : moviesList) {
			/*
			 * if (item.getIdBackend().equals(0)) { MovieSearchItem movieItem =
			 * wsMovieDAO.getSearchResultsMovie(item.getOriginalName()); if
			 * (movieItem != null && movieItem.getResults() > 0) { if
			 * (StringUtils.isNotEmpty(movieItem.getMovieName()) ||
			 * StringUtils.isNotEmpty(movieItem.getOriginalTitle())) {
			 * persistenceDAO.updateDatasMovie(item, movieItem); }
			 *
			 * } else { persistenceDAO.updateIdAllocine(item); } }
			 */
			listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
		}

		return listMoviesDTO;
	}

	@Override
	public void persistMovie(String movieName, String userName) throws TechnicalAccessException {
		// persistenceDAO.persistMovie(movieName, userName);
	}

	@Override
	public List<KindsDTO> getKinds() throws TechnicalAccessException {
		List<KindsDTO> dto = new ArrayList<KindsDTO>();
		// List<KindItem> kinds = persistenceDAO.getKinds();
		// for (KindItem kind : kinds) {
		// dto.add(dtoFactory.buildKindsDTO(kind));
		// }
		return dto;
	}

	@Override
	public List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException {
		List<CatalogItemDTO> listMoviesDTO = new ArrayList<CatalogItemDTO>();
		// List<MovieUserEntityItem> moviesList = persistenceDAO
		// .getMoviesByKind(kind);
		// for (MovieUserEntityItem item : moviesList) {
		// listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
		// }
		return listMoviesDTO;
	}

	@Override
	public List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException {
		List<SearchItemDTO> listMoviesDTO = new ArrayList<SearchItemDTO>();
		List<MovieItem> items = wsMovieDAO.getSearchAllResultsMovie(movieName);
		if (items.size() == 0) {
			// throw new
			// MovieNotFoundException(messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND,
			// new String[] { movieName }));
			return null;
		} else {
			for (MovieItem item : items) {
				listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
			}
		}
		return listMoviesDTO;
	}

	@Override
	public void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException {
		movieDAO.saveUserMovie(movieUserEntity);
	}

	@Override
	public void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException {
		movieDAO.saveMovie(movieEntity);
	}

}
