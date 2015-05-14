package com.xtt.mediatheque.model;

import java.util.List;

public interface MovieItem {

	String getMovieName();

	String getOriginalTitle();

	String getTitle();

	String getReleaseYear();

	String getSynopsis();

	List<ActorsItem> getActors();

	List<DirectorsItem> getDirectors();

	String getURLPoster();

	float getUserRatings();

	List<ProductionCountryItem> getCountries();

	List<KindItem> getGenres();

	String getIdBackend();

	String getURLYoutube();

}
