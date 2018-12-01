package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.MovieUserDAO;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.manager.MovieManager;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.service.MovieService;

/**
 * Implementation of {@link MovieService}
 *
 * @author Eric Morel
 */
@Service
public class MovieServiceImpl implements MovieService {

	private WSMovieDAO wsMovieDAO;
	private MovieUserDAO movieUserDAO;
	private MovieDAO movieDAO;
	private MovieDTOFactory dtoFactory;
	private MovieManager movieManager;
	private MessageUtils messages;

	@Autowired
	public MovieServiceImpl(WSMovieDAO wsMovieDAO, MovieUserDAO movieUserDAO, MovieDAO movieDAO,
			MovieDTOFactory dtoFactory, MovieManager movieManager, MessageUtils messages) {
		this.wsMovieDAO = wsMovieDAO;
		this.movieUserDAO = movieUserDAO;
		this.movieDAO = movieDAO;
		this.dtoFactory = dtoFactory;
		this.movieManager = movieManager;
		this.messages = messages;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CatalogItemDTO> getAllMovies() {
		List<CatalogItemDTO> listMoviesDTO = new ArrayList<>();
		List<MovieUserEntity> movies = movieUserDAO.findAll();

		for (MovieUserEntity item : movies) {
			if (item.getMovie() == 0) {
				MovieSearchItem movieItem = wsMovieDAO.getSearchResultsMovie(item.getOriginalName());
				if (movieItem != null && movieItem.getResults() > 0 && (!StringUtils.isEmpty(movieItem.getMovieName())
						|| !StringUtils.isEmpty(movieItem.getOriginalTitle()))) {
					movieManager.updateDatasMovie(item, movieItem);
				}
			}
			listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
		}

		return listMoviesDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentMovieDTO getContentMovie(long movieId) throws MovieNotFoundException {
		Optional<MovieEntity> optMovie = movieDAO.findById(movieId);
		MovieItem movieItem = optMovie
				.filter(movie -> StringUtils.isEmpty(movie.getReleaseYear())
						|| StringUtils.isEmpty(movie.getSynopsis()))
				.map(movie -> wsMovieDAO.getContentMovie(movieId))
				.orElseThrow(() -> new MovieNotFoundException(messages.getMessageWithParameters(
						MediathequeConstants.MOVIE_NOT_FOUND, new String[] { String.valueOf(movieId) })));
		movieManager.updateFullDatas(optMovie, movieItem);
		return dtoFactory.buildFullMovieDTO(movieItem);
	}

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
