package com.xtt.mediatheque.model.entity;

import java.util.Date;
import java.util.List;

public interface MovieUserEntityItem {

	List<String> getActors();

	List<String> getCountries();

	String getCover();

	List<String> getDirectors();

	List<String> getGenres();

	long getIdBackend();

	String getMovieName();

	String getOriginalName();

	String getMovieTitle();

	String getReleaseYear();

	String getSynopsis();

	Date getTimestampCreationDate();

	String getUrlPoster();

	String getURLYoutube();

	String getUserName();

	String getSupport();

}
