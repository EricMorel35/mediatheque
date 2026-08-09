package com.xtt.mediatheque.manager;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.xtt.mediatheque.dao.ActorDAO;
import com.xtt.mediatheque.dao.CountryDAO;
import com.xtt.mediatheque.dao.DirectorDAO;
import com.xtt.mediatheque.dao.KindDAO;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.ProductionCountryItem;

/**
 * Unit tests for {@link MovieManager}.
 *
 * @author Eric Morel
 */
@ExtendWith(MockitoExtension.class)
class MovieManagerTest {

	@Mock
	private MovieDAO movieDAO;

	@Mock
	private KindDAO kindDAO;

	@Mock
	private ActorDAO actorDAO;

	@Mock
	private DirectorDAO directorDAO;

	@Mock
	private CountryDAO countryDAO;

	@InjectMocks
	private MovieManager movieManager;

	@Test
	void updateFullDatas_whenMoviePresent_updatesFieldsAndPersistsAllRelations() {
		MovieEntity movie = new MovieEntity();
		Optional<MovieEntity> optMovie = Optional.of(movie);

		// Longer than 255 chars: MovieManager truncates the synopsis with
		// substring(0, 255), which throws for shorter input (see the
		// dedicated test below) -- use a safely long value here.
		String longSynopsis = "a".repeat(300);

		MovieItem movieItem = mock(MovieItem.class);
		when(movieItem.getURLYoutube()).thenReturn("https://youtube.com/watch?v=abc");
		when(movieItem.getURLPoster()).thenReturn("/poster.jpg");
		when(movieItem.getSynopsis()).thenReturn(longSynopsis);

		KindItem genre = mock(KindItem.class);
		when(genre.getName()).thenReturn("Action");
		ActorsItem actor = mock(ActorsItem.class);
		when(actor.getName()).thenReturn("Keanu Reeves");
		DirectorsItem director = mock(DirectorsItem.class);
		when(director.getName()).thenReturn("Lana Wachowski");
		ProductionCountryItem country = mock(ProductionCountryItem.class);
		when(country.getName()).thenReturn("USA");

		when(movieItem.getGenres()).thenReturn(List.of(genre));
		when(movieItem.getActors()).thenReturn(List.of(actor));
		when(movieItem.getDirectors()).thenReturn(List.of(director));
		when(movieItem.getCountries()).thenReturn(List.of(country));

		movieManager.updateFullDatas(optMovie, movieItem);

		assertThat(movie.getUrlYoutube()).isEqualTo("https://youtube.com/watch?v=abc");
		assertThat(movie.getUrlCover()).isEqualTo("/poster.jpg");
		assertThat(movie.getSynopsis()).isEqualTo(longSynopsis.substring(0, 255));

		verify(kindDAO).save(any(MovieKindsEntity.class));
		verify(actorDAO).save(any(MovieActorsEntity.class));
		verify(directorDAO).save(any(MovieDirectorsEntity.class));
		verify(countryDAO).save(any(MovieCountryEntity.class));
		verify(movieDAO).save(movie);
	}

	@Test
	void updateFullDatas_whenMovieAbsent_doesNothingAndSavesNothing() {
		MovieItem movieItem = mock(MovieItem.class);

		movieManager.updateFullDatas(Optional.empty(), movieItem);

		verify(movieDAO, never()).save(any());
		verify(kindDAO, never()).save(any());
		verify(actorDAO, never()).save(any());
		verify(directorDAO, never()).save(any());
		verify(countryDAO, never()).save(any());
	}

	@Test
	void updateFullDatas_whenSynopsisShorterThan255Chars_throwsIndexOutOfBounds() {
		// Documents an existing edge case: synopsis.substring(0, 255) blows
		// up for any synopsis under 255 characters instead of just taking
		// what's available.
		MovieEntity movie = new MovieEntity();
		MovieItem movieItem = mock(MovieItem.class);
		when(movieItem.getSynopsis()).thenReturn("Too short");

		assertThatThrownBy(() -> movieManager.updateFullDatas(Optional.of(movie), movieItem))
				.isInstanceOf(StringIndexOutOfBoundsException.class);
	}

}
