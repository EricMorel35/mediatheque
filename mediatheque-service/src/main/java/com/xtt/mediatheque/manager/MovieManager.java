package com.xtt.mediatheque.manager;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.service.MovieService;

@Component
public class MovieManager {

	@Autowired
	private MovieService movieService;

	@Autowired
	private WSMovieDAO wsMovieDAO;

	public void saveMovie(String movieName) {
		MovieSearchItem movieItem = wsMovieDAO.getSearchResultsMovie(movieName);
		if (movieItem.getResults() > 1) {
			MovieEntity movieEntity = new MovieEntity();

			movieEntity.setBackendId(Integer.valueOf(movieItem.getIdBackend()));
			if (StringUtils.isNotEmpty(movieItem.getTitle())) {
				movieEntity.setMovieTitle(movieItem.getTitle());
			} else {
				movieEntity.setMovieTitle(movieItem.getOriginalTitle());
			}
			if (StringUtils.isNotEmpty(movieItem.getReleaseYear())) {
				movieEntity.setReleaseYear(Integer.valueOf(movieItem.getReleaseYear()));
			} else {
				movieEntity.setReleaseYear(0);
			}

			try {
				movieService.saveMovie(movieEntity);
			} catch (TechnicalAccessException e) {
				// TODO Log.
			}

			MovieUserEntity movieUserEntity = new MovieUserEntity();
			movieUserEntity.setOriginalName(movieName);
			movieUserEntity.setCreationDate(new Date());
			movieUserEntity.setIdBackend(movieEntity);

			try {
				movieService.saveUserMovie(movieUserEntity);
			} catch (TechnicalAccessException e) {
				// TODO Log.
			}
		} else {
			// TODO Log.
		}
	}

}
