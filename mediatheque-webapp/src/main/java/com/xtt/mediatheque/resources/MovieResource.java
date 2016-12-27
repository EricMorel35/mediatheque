package com.xtt.mediatheque.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieResource {

	// @Autowired
	// private MovieService movieService;

	/*
	 * @GetMapping("/getContentMovie/{movieId}") public ContentMovieDTO
	 * getContentMovie(@RequestParam("movieId") final String movieId) throws
	 * TechnicalAccessException, MovieNotFoundException, FonctionnalException,
	 * MessageException { // ContentMovieDTO response =
	 * movieService.getContentMovie(movieId); System.out.println("Toto"); return
	 * null; }
	 *
	 * @GetMapping("/getAllMovies") public List<CatalogItemDTO> getAllMovies()
	 * throws MessageException { // return movieService.getAllMovies(); return
	 * null; }
	 *
	 * @GetMapping("/getMoviesByKind") public List<CatalogItemDTO>
	 * getMoviesByKind(@RequestParam("kind") final String kind) throws
	 * TechnicalAccessException { // return movieService.getMoviesByKind(kind);
	 * return null; }
	 *
	 * @GetMapping("/searchMovie/{movieName}") public List<SearchItemDTO>
	 * searchMovieByName(@RequestParam("movieName") final String movieName)
	 * throws MessageException, MovieNotFoundException { // return
	 * movieService.searchMovieByName(movieName); return null; }
	 *
	 * @PostMapping(value = "/getContentMovie/{movieId}") public void
	 * addMovie(@RequestParam("movieName") final String movieName,
	 *
	 * @RequestParam("userName") final String
	 * userName, @RequestParam("supportName") final String supportName) throws
	 * FonctionnalException, TechnicalAccessException {
	 *
	 * }
	 */

	@GetMapping("/getContentMovie/{movieId}")
	public String toto(@RequestParam("movieId") String movieId) {
		return movieId;
	}

}
