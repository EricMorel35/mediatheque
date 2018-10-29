package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.manager.MovieManager;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;
import com.xtt.mediatheque.service.MovieService;

/**
 * Implementation of {@link MovieService}
 *
 * @author Eric Morel
 */
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private WSMovieDAO wsMovieDAO;

	@Autowired
	private MovieUserDAO movieUserDAO;

	@Autowired
	private MovieDAO movieDAO;

	@Autowired
	private MovieDTOFactory dtoFactory;

	@Autowired
	private MovieManager movieManager;
	
	@Autowired
	private MessageUtils messages;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtt.mediatheque.service.MovieService#getAllMovies()
	 */
	@Override
	public List<CatalogItemDTO> getAllMovies() throws TechnicalAccessException {
		List<CatalogItemDTO> listMoviesDTO = new ArrayList<>();
		List<MovieUserEntity> movies = movieUserDAO.findAll();

		for (MovieUserEntity item : movies) {
			if (item.getMovie() == 0) {
				MovieSearchItem movieItem = wsMovieDAO.getSearchResultsMovie(item.getOriginalName());
				if (movieItem != null && movieItem.getResults() > 0) {
					if (!StringUtils.isEmpty(movieItem.getMovieName())
							|| !StringUtils.isEmpty(movieItem.getOriginalTitle())) {
						this.updateDatasMovie(item, movieItem);
					}

				} else {
//					movieDAO.updateIdBackend(item);
				}
			}
			listMoviesDTO.add(dtoFactory.buildLightMovieDTO(item));
		}

		return listMoviesDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xtt.mediatheque.service.MovieService#getAllMovies()
	 */
	@Override
	public ContentMovieDTO getContentMovie(long movieId) throws MovieNotFoundException, TechnicalAccessException {
		Optional<MovieEntity> optMovie = movieDAO.findById(movieId);
		MovieItem movieItem = optMovie.filter(movie -> StringUtils.isEmpty(movie.getReleaseYear()) || StringUtils.isEmpty(movie.getSynopsis()))
			                          .map(movie -> wsMovieDAO.getContentMovie(movieId))
			                          .orElseThrow(() -> new MovieNotFoundException(messages.getMessageWithParameters(MediathequeConstants.MOVIE_NOT_FOUND, new String[] { String.valueOf(movieId) })));
		movieManager.updateFullDatas(optMovie, movieItem);
		
		return null;
	}

	private void updateDatasMovie(MovieUserEntity item, MovieSearchItem movieItem) {
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setMovieId(movieItem.getIdBackend());

		if (!StringUtils.isEmpty(movieItem.getTitle())) {
			movieEntity.setMovieTitle(movieItem.getTitle());
		} else {
			movieEntity.setMovieTitle(movieItem.getOriginalTitle());
		}
		if (!StringUtils.isEmpty(movieItem.getReleaseYear())) {
			movieEntity.setReleaseYear(Integer.valueOf(movieItem.getReleaseYear()));
		} else {
			movieEntity.setReleaseYear(0);
		}

		movieEntity.setSynopsis("");
		movieEntity.setUrlYoutube("");
		movieEntity.setUrlCover("");

		movieDAO.save(movieEntity);

		item.setMovie(movieEntity.getMovieId());
		movieUserDAO.save(item);
	}

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
