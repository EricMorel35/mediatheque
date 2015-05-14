package com.xtt.mediatheque.dto;

import java.util.List;

public class ContentMovieDTO {

	private String movieName;
	private String releaseYear;
	private String synopsis;
	private List<String> actors;
	private List<String> directors;
	private long creationDate;
	private String userName;
	private String poster;
	private List<String> countries;
	private List<String> genres;
	private String urlYoutube;
	private String media;

	public List<String> getCountries() {
		return countries;
	}

	public void setCountries(final List<String> countries) {
		this.countries = countries;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(final List<String> genres) {
		this.genres = genres;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(final String movieName) {
		this.movieName = movieName;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(final String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(final String synopsis) {
		this.synopsis = synopsis;
	}

	public List<String> getActors() {
		return actors;
	}

	public void setActors(final List<String> actors) {
		this.actors = actors;
	}

	public List<String> getDirectors() {
		return directors;
	}

	public void setDirectors(final List<String> directors) {
		this.directors = directors;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final long creationDate) {
		this.creationDate = creationDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(final String poster) {
		this.poster = poster;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(final String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

}
