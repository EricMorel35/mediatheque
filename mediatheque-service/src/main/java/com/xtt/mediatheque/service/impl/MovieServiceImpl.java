package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.MovieUserDAO;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.exceptions.MessageException;
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
	public Page<CatalogItemDTO> getAllMovies(Pageable pageable) {
		Page<MovieUserEntity> movies = movieUserDAO.findAll(pageable);

		return movies.map(new Function<MovieUserEntity, CatalogItemDTO>() {
			@Override
			public CatalogItemDTO apply(MovieUserEntity entity) {
				return CatalogItemDTO.builder().title(entity.getMovieName()).addingDate(new Date())
						.id(entity.getMovie().getMovieId()).urlCover(entity.getMovie().getUrlCover())
						.releaseYear(entity.getMovie().getReleaseYear()).build();
			}
		});
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMovie(MovieSearchItem movie) {
		MovieUserEntity movieUserEntity = new MovieUserEntity();
		movieUserEntity.setMovieName(movie.getMovieName());
		movieUserEntity.setOriginalName(movie.getOriginalTitle());

		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setMovieTitle(movie.getMovieName());
		movieEntity.setMovieId(movie.getIdBackend());
		movieEntity.setReleaseYear(Integer.valueOf(movie.getReleaseYear()));
		movieEntity.setMovieUser(movieUserEntity);

		movieUserDAO.save(movieUserEntity);
	}

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
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SearchItemDTO> searchMovieByName(String movieName) throws MessageException, MovieNotFoundException {
		List<SearchItemDTO> listMoviesDTO = new ArrayList<>();
		List<MovieItem> items = wsMovieDAO.getSearchAllResultsMovie(movieName);
		if (CollectionUtils.isEmpty(items)) {
			throw new MovieNotFoundException(messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND,
					new String[] { movieName }));
		} else {
			for (MovieItem item : items) {
				listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
			}
		}
		return listMoviesDTO;
	}

}
