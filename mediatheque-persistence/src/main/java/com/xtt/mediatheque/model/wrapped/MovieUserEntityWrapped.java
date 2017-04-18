package com.xtt.mediatheque.model.wrapped;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public class MovieUserEntityWrapped implements MovieUserEntityItem {

	private final MovieUserEntity movieEntity;

	public MovieUserEntityWrapped(final MovieUserEntity movieEntity) {
		this.movieEntity = movieEntity;
	}

	@Override
	public List<String> getActors() {
		List<String> actorsList = new ArrayList<String>();
		for (MovieActorsEntity actor : movieEntity.getIdBackend().getActors()) {
			actorsList.add(actor.getPk().getActor());
		}
		return actorsList;
	}

	@Override
	public List<String> getCountries() {
		List<String> countriesList = new ArrayList<String>();
		for (MovieCountryEntity country : movieEntity.getIdBackend().getCountries()) {
			countriesList.add(country.getPk().getCountry());
		}
		return countriesList;
	}

	@Override
	public String getCover() {
		return movieEntity.getIdBackend().getUrlCover();
	}

	@Override
	public List<String> getDirectors() {
		List<String> directorsList = new ArrayList<String>();
		for (MovieDirectorsEntity director : movieEntity.getIdBackend().getDirectors()) {
			directorsList.add(director.getPk().getDirector());
		}
		return directorsList;
	}

	@Override
	public List<String> getGenres() {
		List<String> genresList = new ArrayList<String>();
		for (MovieKindsEntity kind : movieEntity.getIdBackend().getKinds()) {
			genresList.add(kind.getPk().getKind());
		}
		return genresList;
	}

	@Override
	public Integer getIdBackend() {
		return movieEntity.getIdBackend().getBackendId();
	}

	@Override
	public String getMovieName() {
		return movieEntity.getMovieName();
	}

	@Override
	public String getMovieTitle() {
		return movieEntity.getIdBackend().getMovieTitle();
	}

	@Override
	public String getReleaseYear() {
		return String.valueOf(movieEntity.getIdBackend().getReleaseYear());
	}

	@Override
	public String getSynopsis() {
		return movieEntity.getIdBackend().getSynopsis();
	}

	@Override
	public Date getTimestampCreationDate() {
		return movieEntity.getCreationDate();
	}

	@Override
	public String getUrlPoster() {
		return movieEntity.getIdBackend().getUrlCover();
	}

	@Override
	public String getURLYoutube() {
		return movieEntity.getIdBackend().getUrlYoutube();
	}

	@Override
	public String getUserName() {
		// return movieEntity.getUser().getNom();
		return "";
	}

	@Override
	public String getSupport() {
		return movieEntity.getSupport() != null ? movieEntity.getSupport().getMedia() : "";
	}

	@Override
	public String getOriginalName() {
		return movieEntity.getOriginalName();
	}

}
