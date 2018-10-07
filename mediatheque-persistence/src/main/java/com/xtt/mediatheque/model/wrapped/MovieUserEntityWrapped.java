package com.xtt.mediatheque.model.wrapped;

import java.util.Date;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public class MovieUserEntityWrapped implements MovieUserEntityItem {

	private final MovieUserEntity movieEntity;

	public MovieUserEntityWrapped(final MovieUserEntity movieEntity) {
		this.movieEntity = movieEntity;
	}

//	@Override
//	public List<String> getActors() {
//		List<String> actorsList = new ArrayList<String>();
//		for (MovieActorsEntity actor : movieEntity.getId().getActors()) {
//			actorsList.add(actor.getPk().getActor());
//		}
//		return actorsList;
//	}
//
//	@Override
//	public List<String> getCountries() {
//		List<String> countriesList = new ArrayList<String>();
//		for (MovieCountryEntity country : movieEntity.getIdBackend().getCountries()) {
//			countriesList.add(country.getPk().getCountry());
//		}
//		return countriesList;
//	}

	@Override
	public String getCover() {
		return "";
	}

//	@Override
//	public List<String> getDirectors() {
//		List<String> directorsList = new ArrayList<String>();
//		for (MovieDirectorsEntity director : movieEntity.getIdBackend().getDirectors()) {
//			directorsList.add(director.getPk().getDirector());
//		}
//		return directorsList;
//	}
//
//	@Override
//	public List<String> getGenres() {
//		List<String> genresList = new ArrayList<String>();
//		for (MovieKindsEntity kind : movieEntity.getIdBackend().getKinds()) {
//			genresList.add(kind.getPk().getKind());
//		}
//		return genresList;
//	}

	@Override
	public long getIdBackend() {
		return 0L;
	}

	@Override
	public String getMovieName() {
		return "";
	}

	@Override
	public String getMovieTitle() {
		return "";
	}

	@Override
	public String getReleaseYear() {
		return "";
	}

	@Override
	public String getSynopsis() {
		return "";
	}

	@Override
	public Date getTimestampCreationDate() {
		return null;
	}

	@Override
	public String getUrlPoster() {
		return "";
	}

	@Override
	public String getURLYoutube() {
		return "";
	}

//	@Override
//	public String getUserName() {
//		// return movieEntity.getUser().getNom();
//		return "";
//	}
//
//	@Override
//	public String getSupport() {
//		return "";
//	}

	@Override
	public String getOriginalName() {
		return "";
	}

}
