package com.xtt.mediatheque.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.MovieUserDAO;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;

@Service
public class MovieManager {
	
	@Autowired
	private MovieDAO movieDAO;
	
	@Autowired
	private MovieUserDAO movieUserDAO;
	
	public void updateFullDatas(Optional<MovieEntity> optMovie, MovieItem optMovieItem) {
		optMovie.ifPresent(movie -> { 
			movie.setUrlYoutube(optMovieItem.getURLYoutube());
			movie.setUrlCover(optMovieItem.getURLPoster());
			movie.setSynopsis(optMovieItem.getSynopsis().substring(0, 255));
			movieDAO.save(movie);
		});
	}
	
	public void updateDatasMovie(MovieUserEntity item, MovieSearchItem movieItem) {
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

}
