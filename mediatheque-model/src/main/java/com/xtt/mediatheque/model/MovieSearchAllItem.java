package com.xtt.mediatheque.model;

import java.util.List;

public interface MovieSearchAllItem {

	List<String> getMovieName();

	List<String> getOriginalTitle();

	List<String> getTitle();

	List<String> getReleaseYear();

	List<String> getURLPoster();

	float[] getUserRatings();

	List<String> getIdBackend();

	int getResults();

	List<String> getCountries();

}
