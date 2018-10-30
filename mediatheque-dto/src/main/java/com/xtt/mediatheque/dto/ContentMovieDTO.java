package com.xtt.mediatheque.dto;

import java.util.List;

public class ContentMovieDTO {

	private String movieName;
	private String releaseYear;
	private String synopsis;
	private List<String> actors;
	private List<String> directors;
	private long creationDate;
	private String cover;
	private List<String> countries;
	private List<String> genres;
	private String urlYoutube;

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

	public String getCover() {
		return cover;
	}

	public void setCover(final String cover) {
		this.cover = cover;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setUrlYoutube(final String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

}
