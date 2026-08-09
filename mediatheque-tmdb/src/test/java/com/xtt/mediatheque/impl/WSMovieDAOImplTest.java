package com.xtt.mediatheque.impl;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.tmdb.model.Cast;
import com.xtt.mediatheque.tmdb.model.Movie;
import com.xtt.mediatheque.tmdb.model.MoviesList;

/**
 * Unit tests for {@link WSMovieDAOImpl}.
 *
 * @author Eric Morel
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")
class WSMovieDAOImplTest {

	private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/{movie}";
	private static final String SEARCH_URL = "https://api.themoviedb.org/3/search/movie";

	@Mock
	private RestTemplate restTemplate;

	private WSMovieDAOImpl wsMovieDAO;

	@BeforeEach
	void setUp() {
		wsMovieDAO = new WSMovieDAOImpl();
		ReflectionTestUtils.setField(wsMovieDAO, "restTemplate", restTemplate);
		wsMovieDAO.setMovieUrl(MOVIE_URL);
		wsMovieDAO.setSearchUrl(SEARCH_URL);
		wsMovieDAO.setApiKey("test-api-key");
		wsMovieDAO.setUrlCover("https://image.tmdb.org/cover/");
		wsMovieDAO.setUrlYoutube("https://youtube.com/watch?v=");
	}

	@Test
	void getContentMovie_mapsRestTemplateResponseToMovieItem() {
		Movie movie = new Movie();
		movie.setTitle("The Matrix");
		movie.setOriginal_title("The Matrix");
		movie.setOverview("A computer hacker learns...");
		movie.setCasts(new Cast());

		when(restTemplate.getForEntity(eq(MOVIE_URL), eq(Movie.class), any(Object.class)))
				.thenReturn(ResponseEntity.ok(movie));

		MovieItem result = wsMovieDAO.getContentMovie(603L);

		assertThat(result.getMovieName()).isEqualTo("The Matrix");
		assertThat(result.getOriginalTitle()).isEqualTo("The Matrix");
		assertThat(result.getSynopsis()).isEqualTo("A computer hacker learns...");
	}

	@Test
	void getSearchResultsMovie_mapsFirstResult() {
		Movie movie = new Movie();
		movie.setTitle("Inception");

		MoviesList moviesList = new MoviesList();
		moviesList.setResults(List.of(movie));
		when(restTemplate.getForObject(eq(SEARCH_URL), eq(MoviesList.class), any(Map.class)))
				.thenReturn(moviesList);

		MovieSearchItem result = wsMovieDAO.getSearchResultsMovie("Inception");

		assertThat(result.getMovieName()).isEqualTo("Inception");
	}

	@Test
	void getSearchAllResultsMovie_mapsEveryResult() {
		Movie movie1 = new Movie();
		movie1.setTitle("Matrix Reloaded");
		Movie movie2 = new Movie();
		movie2.setTitle("Matrix Revolutions");

		MoviesList moviesList = new MoviesList();
		moviesList.setResults(List.of(movie1, movie2));
		when(restTemplate.getForObject(eq(SEARCH_URL), eq(MoviesList.class), any(Map.class)))
				.thenReturn(moviesList);

		List<MovieItem> result = wsMovieDAO.getSearchAllResultsMovie("Matrix");

		assertThat(result).hasSize(2);
		assertThat(result.get(0).getMovieName()).isEqualTo("Matrix Reloaded");
		assertThat(result.get(1).getMovieName()).isEqualTo("Matrix Revolutions");
	}

	@Test
	void getSearchAllResultsMovie_whenTmdbCallFails_throwsNullPointerException() {
		// Documents an existing edge case: on RestClientException the
		// exception is swallowed and a bare `new MoviesList()` is returned
		// (results == null), so the caller's iteration over getResults()
		// blows up with an NPE instead of getting an empty list back.
		when(restTemplate.getForObject(eq(SEARCH_URL), eq(MoviesList.class), any(Map.class)))
				.thenThrow(new RestClientException("TMDB is down"));

		assertThatThrownBy(() -> wsMovieDAO.getSearchAllResultsMovie("Matrix"))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	void getSearchResultsMovie_whenTmdbCallFails_returnsWrapperThatThrowsOnFirstAccess() {
		// Same underlying issue as above: the DAO call itself doesn't throw,
		// but the MovieSearchWrapped it returns blows up as soon as any of
		// its getters are used, since it wraps a MoviesList with no results.
		when(restTemplate.getForObject(eq(SEARCH_URL), eq(MoviesList.class), any(Map.class)))
				.thenThrow(new RestClientException("TMDB is down"));

		MovieSearchItem result = wsMovieDAO.getSearchResultsMovie("Matrix");

		assertThat(result).isNotNull();
		assertThatThrownBy(result::getMovieName).isInstanceOf(NullPointerException.class);
	}

}
