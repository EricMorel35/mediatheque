package com.xtt.mediatheque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.service.MovieService;

@RestController
public class MovieResource {

	@Autowired
	private MovieService movieService;

	@Autowired
	private MessageUtils messages;

	@GetMapping("getContentMovie/{movieId}")
	public ResponseEntity<ContentMovieDTO> getContentMovie(@PathVariable("movieId") final String movieId)
			throws MovieNotFoundException, FonctionnalException {
		long movie = 0;
		try {
			movie = Long.valueOf(movieId);
		} catch (NumberFormatException e) {
			throw new FonctionnalException(messages.getMessage(MediathequeConstants.MOVIE_NUMBER));
		}
		return new ResponseEntity<>(movieService.getContentMovie(movie), HttpStatus.OK);
	}

	@GetMapping("getAllMovies")
	public ResponseEntity<List<CatalogItemDTO>> getAllMovies() {
		List<CatalogItemDTO> movies = movieService.getAllMovies();
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}

	@GetMapping("searchMovie/{movieName}")
	public ResponseEntity<List<SearchItemDTO>> searchMovieByName(@PathVariable("movieName") final String movieName)
			throws MessageException, MovieNotFoundException {
//		return new ResponseEntity<>(movieService.searchMovieByName(movieName), HttpStatus.OK);
		return null;
	}

	@GetMapping("getMoviesByKind/{kind}")
	public List<CatalogItemDTO> getMoviesByKind(@PathVariable("kind") final String kind)
			throws TechnicalAccessException {
//		return movieService.getMoviesByKind(kind);
		return null;
	}

	@PostMapping("getContentMovie/{movieId}")
	public void addMovie(@RequestParam("movieName") final String movieName,
			@RequestParam("userName") final String userName, @RequestParam("supportName") final String supportName)
			throws FonctionnalException, TechnicalAccessException {

	}
}
