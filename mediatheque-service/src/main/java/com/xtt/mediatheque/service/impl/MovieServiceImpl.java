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
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
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

//	@Autowired
//	private WSMovieDAO wsMovieDAO;
//
//	@Autowired
//	private MovieDTOFactory dtoFactory;
//
//	@Autowired
//	private PersistenceDAO persistenceDAO;
//
//	@Autowired
//	private MessageUtils messages;
//
//	@Autowired
//	private MovieDAO movieDAO;
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<CatalogItemDTO> getAllMovies() throws MessageException, TechnicalAccessException {
//		return loadAllMovies();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public ContentMovieDTO getContentMovie(final long movieId)
//			throws MovieNotFoundException, TechnicalAccessException, MessageException {
//
//		MovieUserEntityItem movieEntity = movieDAO.getMovieByExternalId(movieId);
//		if (movieEntity != null) {
//			if (StringUtils.isEmpty(movieEntity.getReleaseYear()) || StringUtils.isEmpty(movieEntity.getSynopsis())) {
//				MovieItem movieItem = wsMovieDAO.getContentMovie(movieId);
//				movieDAO.updateFullDatas(movieEntity, movieItem);
//			}
//			return dtoFactory.buildFullMovieDTO(movieEntity);
//		} else {
//			throw new MovieNotFoundException(messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND,
//					new String[] { String.valueOf(movieId) }));
//		}
//	}
//
//	@Override
//	public String getCoverByNameFromDisk(final String name) {
//		// return persistenceDAO.getCoverByNameFromDisk(name);
//		return "";
//	}
//
//	private List<CatalogItemDTO> loadAllMovies() throws MessageException, TechnicalAccessException {
//		List<CatalogItemDTO> listMoviesDTO = new ArrayList<CatalogItemDTO>();
//		List<MovieUserEntityItem> moviesList = movieDAO.getAllMovies();
//		for (MovieUserEntityItem item : moviesList) {
//			if (item.getIdBackend() == 0) {
//				// MovieSearchItem movieItem =
//				// wsMovieDAO.getSearchResultsMovie(item.getOriginalName());
//				// if (movieItem != null && movieItem.getResults() > 0) {
//				// if (StringUtils.isNotEmpty(movieItem.getMovieName())
//				// || StringUtils.isNotEmpty(movieItem.getOriginalTitle())) {
//				// movieDAO.updateDatasMovie(item, movieItem);
//				// }
//				//
//				// } else {
//				// movieDAO.updateIdBackend(item);
//				// }
//			}
//			listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
//		}
//
//		return listMoviesDTO;
//	}
//
//	@Override
//	public void persistMovie(String movieName, String userName) throws TechnicalAccessException {
//		// persistenceDAO.persistMovie(movieName, userName);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<CatalogItemDTO> getMoviesByKind(String kind) throws TechnicalAccessException {
//		List<CatalogItemDTO> listMoviesDTO = new ArrayList<CatalogItemDTO>();
//		List<MovieUserEntityItem> moviesList = movieDAO.getMoviesByKind(kind);
//		for (MovieUserEntityItem item : moviesList) {
//			listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
//		}
//		return listMoviesDTO;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	@Override
//	public List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException {
//		List<SearchItemDTO> listMoviesDTO = new ArrayList<SearchItemDTO>();
//		List<MovieItem> items = wsMovieDAO.getSearchAllResultsMovie(movieName);
//		if (items.size() == 0) {
//			throw new MovieNotFoundException(messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND,
//					new String[] { movieName }));
//		} else {
//			for (MovieItem item : items) {
//				listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
//			}
//		}
//		return listMoviesDTO;
//	}
//
//	@Override
//	public void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException {
//		movieDAO.saveUserMovie(movieUserEntity);
//	}
//
//	@Override
//	public void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException {
//		movieDAO.saveMovie(movieEntity);
//	}

}
