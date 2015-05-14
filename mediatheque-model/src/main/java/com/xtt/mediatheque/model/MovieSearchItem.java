package com.xtt.mediatheque.model;

import java.util.List;

public interface MovieSearchItem {
	String getMovieName();

	String getOriginalTitle();

	String getTitle();

	String getReleaseYear();

	String getURLPoster();

	float getUserRatings();

	String getIdBackend();

	int getResults();

	List<String> getCountries();

}
