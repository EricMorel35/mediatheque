package com.xtt.mediatheque.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.service.MovieService;

@Controller
public class MovieResource {

	@Autowired
	private MovieService movieService;

	@GetMapping("/getContentMovie/{movieId}")
	public ResponseEntity<ContentMovieDTO> getContentMovie(@PathVariable("movieId") final String movieId)
			throws TechnicalAccessException, MovieNotFoundException, FonctionnalException, MessageException {
		return new ResponseEntity<ContentMovieDTO>(movieService.getContentMovie(movieId), HttpStatus.OK);
	}

	@GetMapping("/getAllMovies")
	@ResponseBody
	public ResponseEntity<List<CatalogItemDTO>> getAllMovies() throws MessageException, TechnicalAccessException {
		List<CatalogItemDTO> movies = movieService.getAllMovies();
		return new ResponseEntity<List<CatalogItemDTO>>(movies, HttpStatus.OK);
	}

	@GetMapping("/getMoviesByKind")
	public List<CatalogItemDTO> getMoviesByKind(@RequestParam("kind") final String kind)
			throws TechnicalAccessException { // return
												// movieService.getMoviesByKind(kind);
		return null;
	}

	@GetMapping("/searchMovie/{movieName}")
	public List<SearchItemDTO> searchMovieByName(@RequestParam("movieName") final String movieName)
			throws MessageException, MovieNotFoundException { // return
		movieService.searchMovieByName(movieName);
		return null;
	}

	@PostMapping(value = "/getContentMovie/{movieId}")
	public void addMovie(@RequestParam("movieName") final String movieName,

			@RequestParam("userName") final String userName, @RequestParam("supportName") final String supportName)
			throws FonctionnalException, TechnicalAccessException {

	}
}
