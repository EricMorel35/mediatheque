package com.xtt.mediatheque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.FonctionnalException;
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

	@GetMapping("movie/{movieId}")
	public ResponseEntity<ContentMovieDTO> movie(@PathVariable("movieId") final String movieId)
			throws MovieNotFoundException, FonctionnalException {
		long movie = 0;
		try {
			movie = Long.valueOf(movieId);
		} catch (NumberFormatException e) {
			throw new FonctionnalException(messages.getMessage(MediathequeConstants.MOVIE_NUMBER));
		}
		return new ResponseEntity<>(movieService.movie(movie), HttpStatus.OK);
	}

	@GetMapping("movies")
	public Page<CatalogItemDTO> movies(Pageable pageable) {
		return movieService.movies(pageable);
	}

	@GetMapping("searchMovie/{movieName}")
	public ResponseEntity<List<SearchItemDTO>> searchMovieByName(@PathVariable("movieName") final String movieName)
			throws MovieNotFoundException {
		return new ResponseEntity<>(movieService.searchMovieByName(movieName), HttpStatus.OK);
	}

	@GetMapping("moviesByKind/{kind}")
	public List<CatalogItemDTO> getMoviesByKind(@PathVariable("kind") final String kind)
			throws TechnicalAccessException {
		return movieService.getMoviesByKind(kind);
	}

}
