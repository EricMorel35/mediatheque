package com.xtt.mediatheque.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;

@Service
public class MovieManager {
	
	@Autowired
	private MovieDAO movieDAO;
	
	public void updateFullDatas(Optional<MovieEntity> optMovie, MovieItem optMovieItem) {
		optMovie.ifPresent(movie -> { movie.setUrlYoutube(optMovieItem.getURLYoutube()); movieDAO.save(movie);});
	}

}
