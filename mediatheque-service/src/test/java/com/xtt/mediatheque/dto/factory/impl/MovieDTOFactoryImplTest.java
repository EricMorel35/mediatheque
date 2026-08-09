package com.xtt.mediatheque.dto.factory.impl;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;

/**
 * Unit tests for {@link MovieDTOFactoryImpl}.
 *
 * @author Eric Morel
 */
class MovieDTOFactoryImplTest {

	private MovieDTOFactoryImpl factory = new MovieDTOFactoryImpl();

	@Test
	void buildLightMovieDTO_fromMovieItem_mapsTitleReleaseYearAndCover() {
		MovieItem movieItem = mock(MovieItem.class);
		when(movieItem.getMovieName()).thenReturn("Interstellar");
		when(movieItem.getReleaseYear()).thenReturn("2014");
		when(movieItem.getURLPoster()).thenReturn("/interstellar.jpg");

		SearchItemDTO dto = factory.buildLightMovieDTO(movieItem);

		assertThat(dto.getTitle()).isEqualTo("Interstellar");
		assertThat(dto.getReleaseYear()).isEqualTo("2014");
		assertThat(dto.getUrlCover()).isEqualTo("/interstellar.jpg");
	}

	@Test
	void buildFullMovieDTO_mapsActorsDirectorsGenresAndYoutubeUrl() {
		MovieItem movieItem = mock(MovieItem.class);
		when(movieItem.getMovieName()).thenReturn("Interstellar");
		when(movieItem.getReleaseYear()).thenReturn("2014");
		when(movieItem.getSynopsis()).thenReturn("A team of explorers...");
		when(movieItem.getURLYoutube()).thenReturn("https://youtube.com/watch?v=zSWdZVtXT7E");

		ActorsItem actor = mock(ActorsItem.class);
		when(actor.getName()).thenReturn("Matthew McConaughey");
		DirectorsItem director = mock(DirectorsItem.class);
		when(director.getName()).thenReturn("Christopher Nolan");
		KindItem genre = mock(KindItem.class);
		when(genre.getName()).thenReturn("Sci-Fi");

		when(movieItem.getActors()).thenReturn(List.of(actor));
		when(movieItem.getDirectors()).thenReturn(List.of(director));
		when(movieItem.getGenres()).thenReturn(List.of(genre));

		ContentMovieDTO dto = factory.buildFullMovieDTO(movieItem);

		assertThat(dto.getMovieName()).isEqualTo("Interstellar");
		assertThat(dto.getReleaseYear()).isEqualTo("2014");
		assertThat(dto.getSynopsis()).isEqualTo("A team of explorers...");
		assertThat(dto.getActors()).containsExactly("Matthew McConaughey");
		assertThat(dto.getDirectors()).containsExactly("Christopher Nolan");
		assertThat(dto.getGenres()).containsExactly("Sci-Fi");
		assertThat(dto.getUrlYoutube()).isEqualTo("https://youtube.com/watch?v=zSWdZVtXT7E");
	}

	@Test
	void buildLightMovieDTO_fromMovieUserEntity_mapsNameAndLinkedMovieId() {
		MovieEntity movie = new MovieEntity();
		movie.setMovieId(603L);
		MovieUserEntity movieUser = new MovieUserEntity();
		movieUser.setMovieName("The Matrix");
		movieUser.setMovie(movie);

		CatalogItemDTO dto = factory.buildLightMovieDTO(movieUser);

		assertThat(dto.getTitle()).isEqualTo("The Matrix");
		assertThat(dto.getId()).isEqualTo(603L);
	}

}
