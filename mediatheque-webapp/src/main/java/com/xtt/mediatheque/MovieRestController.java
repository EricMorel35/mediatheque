package com.xtt.mediatheque;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.FonctionnalException;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.service.MovieService;

@Path("/movie")
@Controller
public class MovieRestController {

	@Autowired
	private MovieService movieService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getContentMovie/{movieId}")
	public Response getContentMovie(@PathParam("movieId") final String movieId)
			throws TechnicalAccessException, MovieNotFoundException,
			FonctionnalException, MessageException {
		ContentMovieDTO response = movieService.getContentMovie(movieId);

		CacheControl cc = new CacheControl();
		cc.setMaxAge(86400);
		cc.setPrivate(true);

		ResponseBuilder builder = Response.ok(response);
		builder.cacheControl(cc);
		return builder.build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllMovies")
	public List<CatalogItemDTO> getAllMovies() throws MessageException {
		// return movieService.getAllMovies();
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getKinds")
	public List<KindsDTO> getKinds() throws TechnicalAccessException {
		// return movieService.getKinds();
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getMoviesByKind")
	public List<CatalogItemDTO> getMoviesByKind(
			@PathParam("kind") final String kind)
			throws TechnicalAccessException {
		// return movieService.getMoviesByKind(kind);
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchItemDTO> searchMovieByName(
			@PathParam("movieName") final String movieName)
			throws MessageException, MovieNotFoundException {
		// return movieService.searchMovieByName(movieName);
		return null;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/addMovie")
	public void addMovie(@PathParam("movieName") final String movieName,
			@PathParam("userName") final String userName,
			@PathParam("supportName") final String supportName)
			throws FonctionnalException, TechnicalAccessException {

	}

}
