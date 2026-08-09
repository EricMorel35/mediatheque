package com.xtt.mediatheque.service.impl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xtt.mediatheque.WSMovieDAO;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.MovieUserDAO;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.exceptions.MovieNotFoundException;
import com.xtt.mediatheque.manager.MovieManager;
import com.xtt.mediatheque.messages.MessageUtils;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;

/**
 * Unit tests for {@link MovieServiceImpl}.
 *
 * @author Eric Morel
 */
@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

	@Mock
	private WSMovieDAO wsMovieDAO;

	@Mock
	private MovieUserDAO movieUserDAO;

	@Mock
	private MovieDAO movieDAO;

	@Mock
	private MovieDTOFactory dtoFactory;

	@Mock
	private MovieManager movieManager;

	@Mock
	private MessageUtils messages;

	@InjectMocks
	private MovieServiceImpl movieService;

	@Test
	void movies_mapsPageOfEntitiesToCatalogItemDTOs() {
		MovieEntity movie = new MovieEntity();
		movie.setMovieId(42L);
		movie.setUrlCover("/cover.jpg");
		movie.setReleaseYear(2001);

		MovieUserEntity movieUser = new MovieUserEntity();
		movieUser.setMovieName("The Matrix");
		movieUser.setMovie(movie);

		Pageable pageable = PageRequest.of(0, 20);
		when(movieUserDAO.findAll(pageable)).thenReturn(new PageImpl<>(List.of(movieUser), pageable, 1));

		Page<CatalogItemDTO> result = movieService.movies(pageable);

		assertThat(result.getTotalElements()).isEqualTo(1);
		CatalogItemDTO dto = result.getContent().get(0);
		assertThat(dto.getTitle()).isEqualTo("The Matrix");
		assertThat(dto.getId()).isEqualTo(42L);
		assertThat(dto.getUrlCover()).isEqualTo("/cover.jpg");
		assertThat(dto.getReleaseYear()).isEqualTo(2001);
	}

	@Test
	void movie_whenReleaseYearMissing_fetchesFromTmdbAndUpdatesStorage() throws MovieNotFoundException {
		MovieEntity storedMovie = new MovieEntity();
		storedMovie.setReleaseYear(null);
		storedMovie.setSynopsis("Already has a synopsis");
		Optional<MovieEntity> optMovie = Optional.of(storedMovie);
		when(movieDAO.findById(42L)).thenReturn(optMovie);

		MovieItem tmdbItem = mock(MovieItem.class);
		when(wsMovieDAO.getContentMovie(42L)).thenReturn(tmdbItem);

		ContentMovieDTO expectedDto = new ContentMovieDTO();
		when(dtoFactory.buildFullMovieDTO(tmdbItem)).thenReturn(expectedDto);

		ContentMovieDTO result = movieService.movie(42L);

		assertThat(result).isSameAs(expectedDto);
		verify(movieManager).updateFullDatas(optMovie, tmdbItem);
	}

	@Test
	void movie_whenSynopsisMissing_fetchesFromTmdbAndUpdatesStorage() throws MovieNotFoundException {
		MovieEntity storedMovie = new MovieEntity();
		storedMovie.setReleaseYear(2020);
		storedMovie.setSynopsis(null);
		when(movieDAO.findById(42L)).thenReturn(Optional.of(storedMovie));

		MovieItem tmdbItem = mock(MovieItem.class);
		when(wsMovieDAO.getContentMovie(42L)).thenReturn(tmdbItem);
		when(dtoFactory.buildFullMovieDTO(tmdbItem)).thenReturn(new ContentMovieDTO());

		movieService.movie(42L);

		verify(wsMovieDAO).getContentMovie(42L);
	}

	@Test
	void movie_whenMovieDataAlreadyComplete_throwsMovieNotFoundException() {
		// Documents current behavior: Optional.filter() keeps the movie only
		// when data is INCOMPLETE (see MovieServiceImpl.movie javadoc vs
		// implementation) -- a movie whose releaseYear and synopsis are both
		// already populated fails the filter and falls through to
		// orElseThrow, even though it was found in the database. This looks
		// like an inverted condition rather than intended behavior.
		MovieEntity storedMovie = new MovieEntity();
		storedMovie.setReleaseYear(2020);
		storedMovie.setSynopsis("Complete synopsis");
		when(movieDAO.findById(42L)).thenReturn(Optional.of(storedMovie));
		when(messages.getMessageWithParameters(anyString(), any())).thenReturn("Movie not found");

		assertThatThrownBy(() -> movieService.movie(42L)).isInstanceOf(MovieNotFoundException.class);

		verify(wsMovieDAO, never()).getContentMovie(anyLong());
	}

	@Test
	void movie_whenMovieAbsent_throwsMovieNotFoundException() {
		when(movieDAO.findById(42L)).thenReturn(Optional.empty());
		when(messages.getMessageWithParameters(eq("movie.not.found"), any())).thenReturn("Movie 42 not found");

		assertThatThrownBy(() -> movieService.movie(42L)).isInstanceOf(MovieNotFoundException.class)
				.hasMessage("Movie 42 not found");
	}

	@Test
	void saveMovie_linksBothSidesOfTheRelationAndPersists() {
		MovieSearchItem movieSearchItem = mock(MovieSearchItem.class);
		when(movieSearchItem.getMovieName()).thenReturn("Inception");
		when(movieSearchItem.getOriginalTitle()).thenReturn("Inception");
		when(movieSearchItem.getIdBackend()).thenReturn(27205L);
		when(movieSearchItem.getReleaseYear()).thenReturn("2010");
		when(movieSearchItem.getURLPoster()).thenReturn("/inception.jpg");

		movieService.saveMovie(movieSearchItem);

		ArgumentCaptor<MovieUserEntity> captor = ArgumentCaptor.forClass(MovieUserEntity.class);
		verify(movieUserDAO).save(captor.capture());

		MovieUserEntity saved = captor.getValue();
		assertThat(saved.getMovieName()).isEqualTo("Inception");
		assertThat(saved.getMovie()).isNotNull();
		assertThat(saved.getMovie().getMovieId()).isEqualTo(27205L);
		// Both sides of the bidirectional 1:1 relation must be linked,
		// otherwise cascade=ALL has nothing to persist (see saveMovie's
		// own comment).
		assertThat(saved.getMovie().getMovieUser()).isSameAs(saved);
	}

	@Test
	void getMoviesByKind_currentlyAlwaysReturnsAnEmptyList() throws Exception {
		// The real lookup is commented out in MovieServiceImpl; this test
		// documents the current (stub) behavior rather than the intended one.
		List<CatalogItemDTO> result = movieService.getMoviesByKind("Action");

		assertThat(result).isEmpty();
	}

	@Test
	void searchMovieByName_mapsEachResultToADTO() throws MovieNotFoundException {
		MovieItem item1 = mock(MovieItem.class);
		MovieItem item2 = mock(MovieItem.class);
		when(wsMovieDAO.getSearchAllResultsMovie("Matrix")).thenReturn(List.of(item1, item2));

		SearchItemDTO dto1 = new SearchItemDTO();
		SearchItemDTO dto2 = new SearchItemDTO();
		when(dtoFactory.buildLightMovieDTO(item1)).thenReturn(dto1);
		when(dtoFactory.buildLightMovieDTO(item2)).thenReturn(dto2);

		List<SearchItemDTO> result = movieService.searchMovieByName("Matrix");

		assertThat(result).containsExactly(dto1, dto2);
	}

	@Test
	void searchMovieByName_whenNoResults_throwsMovieNotFoundException() {
		when(wsMovieDAO.getSearchAllResultsMovie("Unknown")).thenReturn(List.of());
		when(messages.getMessageWithParameters(eq("movie.not.found"), any())).thenReturn("Unknown not found");

		assertThatThrownBy(() -> movieService.searchMovieByName("Unknown")).isInstanceOf(MovieNotFoundException.class)
				.hasMessage("Unknown not found");
	}

}
