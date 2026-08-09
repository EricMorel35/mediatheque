package com.xtt.mediatheque;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.xtt.mediatheque.dao.movie.MovieUserDAO;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieUserEntity;

/**
 * Integration test for the GET /movies pagination endpoint (MovieResource ->
 * MovieServiceImpl -> MovieUserDAO.findAll(Pageable)). Runs against a real
 * MySQL instance via Testcontainers rather than mocks or an in-memory
 * database, so it exercises the same engine as production and would catch
 * dialect-specific pagination bugs that Spring Data plumbing tests alone
 * would miss.
 *
 * @author Eric Morel
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class MoviePaginationIntegrationTest {

	private static final int TOTAL_MOVIES = 100;

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:latest");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MovieUserDAO movieUserDAO;

	@BeforeEach
	void seedMovies() {
		List<MovieUserEntity> movies = new ArrayList<>();
		for (int i = 1; i <= TOTAL_MOVIES; i++) {
			MovieUserEntity movieUser = new MovieUserEntity();
			movieUser.setMovieName("Test Movie " + i);
			movieUser.setOriginalName("Original Test Movie " + i);
			movieUser.setCreationDate(LocalDate.of(2024, 1, 1));

			MovieEntity movie = new MovieEntity();
			movie.setMovieId(900_000L + i);
			movie.setMovieTitle("Test Movie " + i);
			movie.setReleaseYear(1920 + i);
			movie.setUrlCover("/img/cover-" + i + ".jpg");

			// MovieEntity is the owning side (holds the FK); both sides must
			// be linked for the cascade=ALL on MovieUserEntity.movie to
			// persist it (same pattern as MovieServiceImpl.saveMovie).
			movieUser.setMovie(movie);
			movie.setMovieUser(movieUser);

			movies.add(movieUser);
		}
		movieUserDAO.saveAll(movies);
	}

	@AfterEach
	void cleanUp() {
		movieUserDAO.deleteAll();
	}

	@Test
	void firstPage_returnsRequestedSizeAndTotalMetadata() throws Exception {
		mockMvc.perform(get("/movies").param("page", "0").param("size", "20"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(TOTAL_MOVIES))
				.andExpect(jsonPath("$.totalPages").value(5))
				.andExpect(jsonPath("$.number").value(0))
				.andExpect(jsonPath("$.numberOfElements").value(20))
				.andExpect(jsonPath("$.first").value(true))
				.andExpect(jsonPath("$.last").value(false))
				.andExpect(jsonPath("$.content.length()").value(20))
				.andExpect(jsonPath("$.content[0].title").value("Test Movie 1"))
				.andExpect(jsonPath("$.content[0].id").value(900_001));
	}

	@Test
	void lastPage_returnsPartialRemainder() throws Exception {
		// 100 movies, size 30 -> pages of 30/30/30/10: page 3 (0-indexed) is
		// the partial last page.
		mockMvc.perform(get("/movies").param("page", "3").param("size", "30"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(TOTAL_MOVIES))
				.andExpect(jsonPath("$.totalPages").value(4))
				.andExpect(jsonPath("$.number").value(3))
				.andExpect(jsonPath("$.numberOfElements").value(10))
				.andExpect(jsonPath("$.first").value(false))
				.andExpect(jsonPath("$.last").value(true))
				.andExpect(jsonPath("$.content.length()").value(10));
	}

	@Test
	void pageBeyondAvailableData_returnsEmptyContent() throws Exception {
		mockMvc.perform(get("/movies").param("page", "10").param("size", "20"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(TOTAL_MOVIES))
				.andExpect(jsonPath("$.numberOfElements").value(0))
				.andExpect(jsonPath("$.content.length()").value(0))
				.andExpect(jsonPath("$.last").value(true));
	}

	@Test
	void defaultPagination_usesSpringDataDefaultPageSize() throws Exception {
		mockMvc.perform(get("/movies"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size").value(20))
				.andExpect(jsonPath("$.numberOfElements").value(20));
	}

}
