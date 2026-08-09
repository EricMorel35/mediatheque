package com.xtt.mediatheque.resources;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.service.MovieService;

/**
 * Web slice tests for {@link MovieResource}, including how
 * {@link com.xtt.mediatheque.exception.GlobalExceptionHandler} turns
 * exceptions thrown by {@link MovieService} into HTTP responses.
 *
 * @author Eric Morel
 */
@WebMvcTest(MovieResource.class)
class MovieResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private MovieService movieService;

	// Unused by MovieResource's current methods, but the field is
	// @Autowired on the controller, so the web slice context still needs a
	// bean for it.
	@MockitoBean
	private MessageUtils messages;

	@Test
	void movie_withValidId_returnsMovieDetails() throws Exception {
		ContentMovieDTO dto = new ContentMovieDTO();
		dto.setMovieName("The Matrix");
		dto.setReleaseYear("1999");
		when(movieService.movie(603L)).thenReturn(dto);

		mockMvc.perform(get("/movie/603")).andExpect(status().isOk())
				.andExpect(jsonPath("$.movieName").value("The Matrix"))
				.andExpect(jsonPath("$.releaseYear").value("1999"));
	}

	@Test
	void movie_withNonNumericId_returns400() throws Exception {
		when(messages.getMessage(MediathequeConstants.MOVIE_NUMBER)).thenReturn("Id must be numeric");

		mockMvc.perform(get("/movie/not-a-number")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Id must be numeric"));
	}

	@Test
	void movie_whenServiceReportsNotFound_returns404() throws Exception {
		when(movieService.movie(999L)).thenThrow(new MovieNotFoundException("Movie 999 not found"));

		mockMvc.perform(get("/movie/999")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Movie 999 not found"));
	}

	@Test
	void movies_forwardsPageableToServiceAndReturnsItsResult() throws Exception {
		CatalogItemDTO item = CatalogItemDTO.builder().id(1L).title("Inception").build();
		Pageable pageable = PageRequest.of(1, 5);
		Page<CatalogItemDTO> page = new PageImpl<>(List.of(item), pageable, 6);
		when(movieService.movies(pageable)).thenReturn(page);

		mockMvc.perform(get("/movies").param("page", "1").param("size", "5")).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].title").value("Inception"))
				.andExpect(jsonPath("$.totalElements").value(6))
				.andExpect(jsonPath("$.number").value(1));
	}

	@Test
	void searchMovieByName_withResults_returnsThem() throws Exception {
		SearchItemDTO dto = new SearchItemDTO();
		dto.setTitle("Matrix");
		when(movieService.searchMovieByName("Matrix")).thenReturn(List.of(dto));

		mockMvc.perform(get("/searchMovie/Matrix")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Matrix"));
	}

	@Test
	void searchMovieByName_whenNoneFound_returns404() throws Exception {
		when(movieService.searchMovieByName("Unknown")).thenThrow(new MovieNotFoundException("Unknown not found"));

		mockMvc.perform(get("/searchMovie/Unknown")).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value("Unknown not found"));
	}

	@Test
	void getMoviesByKind_returnsServiceResult() throws Exception {
		CatalogItemDTO item = CatalogItemDTO.builder().id(2L).title("Alien").build();
		when(movieService.getMoviesByKind("SciFi")).thenReturn(List.of(item));

		mockMvc.perform(get("/moviesByKind/SciFi")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("Alien"));
	}

	@Test
	void getMoviesByKind_whenTechnicalAccessException_returns400WithCodeAndMessage() throws Exception {
		when(movieService.getMoviesByKind(anyString()))
				.thenThrow(new TechnicalAccessException("ERR-01", "Database unavailable"));

		mockMvc.perform(get("/moviesByKind/Action")).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("ERR-01")).andExpect(jsonPath("$.message").value("Database unavailable"));
	}

}
