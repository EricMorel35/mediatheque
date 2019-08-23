package com.xtt.mediatheque.wrapped;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.ProductionCountryItem;
import com.xtt.mediatheque.tmdb.model.Cast;
import com.xtt.mediatheque.tmdb.model.Genre;
import com.xtt.mediatheque.tmdb.model.Movie;
import com.xtt.mediatheque.tmdb.model.PersonCast;
import com.xtt.mediatheque.tmdb.model.PersonCrew;
import com.xtt.mediatheque.tmdb.model.ProductionCountry;

@Component
public class MovieWrapped implements MovieItem {

	private final Movie movie;

	@Value("urlYoutube")
	private String urlYoutube;

	@Value("urlCover")
	private String urlCover;

	public MovieWrapped(final Movie movie) {
		this.movie = movie;
	}

	@Override
	public List<ActorsItem> getActors() {
		List<ActorsItem> actors = new ArrayList<>();
		Cast cast = movie.getCasts();
		for (PersonCast person : cast.getCast()) {
			actors.add(new ActorsItemWrapped(person));
		}
		return actors;
	}

	@Override
	public List<ProductionCountryItem> getCountries() {
		List<ProductionCountryItem> listCountries = new ArrayList<>();
		List<ProductionCountry> countries = movie.getProduction_countries();
		for (ProductionCountry country : countries) {
			listCountries.add(new ProductionCountryItemWrapped(country));
		}
		return listCountries;
	}

	@Override
	public List<DirectorsItem> getDirectors() {
		List<DirectorsItem> directors = new ArrayList<>();
		Cast cast = movie.getCasts();
		for (PersonCrew person : cast.getCrew()) {
			if ("Director".equalsIgnoreCase(person.getJob())) {
				directors.add(new DirectorsItemWrapped(person));
			}
		}
		return directors;
	}

	@Override
	public List<KindItem> getGenres() {
		List<KindItem> listGenres = new ArrayList<>();
		List<Genre> genres = movie.getGenres();
		for (Genre genre : genres) {
			listGenres.add(new KindItemWrapped(genre));
		}
		return listGenres;
	}

	@Override
	public String getIdBackend() {
		return String.valueOf(movie.getId());
	}

	@Override
	public String getMovieName() {
		return movie.getTitle();
	}

	@Override
	public String getOriginalTitle() {
		return movie.getOriginal_title();
	}

	@Override
	public String getReleaseYear() {
		Date releaseDate = movie.getRelease_date();
		if (releaseDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			return dateFormat.format(releaseDate);
		} else {
			return "";
		}
	}

	@Override
	public String getSynopsis() {
		return movie.getOverview();
	}

	@Override
	public String getTitle() {
		return movie.getTitle();
	}

	@Override
	public String getURLPoster() {
		if (movie.getPoster_path() != null) {
			return urlCover.concat(movie.getPoster_path());
		} else {
			return "";
		}
	}

	@Override
	public String getURLYoutube() {
		if (CollectionUtils.isNotEmpty(movie.getTrailers().getYoutube())
				&& StringUtils.isNotBlank(movie.getTrailers().getYoutube().get(0).getSource())) {
			return urlYoutube.concat(movie.getTrailers().getYoutube().get(0).getSource());
		} else {
			return StringUtils.EMPTY;
		}
	}

	@Override
	public float getUserRatings() {
		return movie.getVote_average();
	}

}
