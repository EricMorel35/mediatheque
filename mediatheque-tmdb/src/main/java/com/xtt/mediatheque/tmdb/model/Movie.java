package com.xtt.mediatheque.tmdb.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {

	@JsonProperty("adult")
	private boolean adult;
	@JsonProperty("backdrop_path")
	private String backdrop_path;
	@JsonProperty("belongs_to_collection")
	private Collection belongs_to_collection;
	@JsonProperty("budget")
	private long budget;
	@JsonProperty("genres")
	private List<Genre> genres;
	@JsonProperty("homepage")
	private String homepage;
	@JsonProperty("id")
	private int id;
	@JsonProperty("imdb_id")
	private String imdb_id;
	@JsonProperty("original_title")
	private String original_title;
	@JsonProperty("overview")
	private String overview;
	@JsonProperty("popularity")
	private float popularity;
	@JsonProperty("poster_path")
	private String poster_path;
	@JsonProperty("production_companies")
	private List<ProductionCompany> production_companies;
	@JsonProperty("production_countries")
	private List<ProductionCountry> production_countries;
	@JsonProperty("page")
	private String page;
	@JsonProperty("release_date")
	private Date release_date;
	@JsonProperty("revenue")
	private int revenue;
	@JsonProperty("runtime")
	private int runtime;
	@JsonProperty("spoken_language")
	private List<Language> spoken_languages;
	@JsonProperty("status")
	private String status;
	@JsonProperty("tagline")
	private String tagline;
	@JsonProperty("title")
	private String title;
	@JsonProperty("vote_average")
	private float vote_average;
	@JsonProperty("vote_count")
	private int vote_count;
	@JsonProperty("casts")
	private Cast casts;
	@JsonProperty("trailers")
	private Trailers trailers;

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(final boolean adult) {
		this.adult = adult;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(final String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

	public Collection getBelongs_to_collection() {
		return belongs_to_collection;
	}

	public void setBelongs_to_collection(final Collection belongs_to_collection) {
		this.belongs_to_collection = belongs_to_collection;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(final long budget) {
		this.budget = budget;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(final List<Genre> genres) {
		this.genres = genres;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(final String homepage) {
		this.homepage = homepage;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getImdb_id() {
		return imdb_id;
	}

	public void setImdb_id(final String imdb_id) {
		this.imdb_id = imdb_id;
	}

	public String getOriginal_title() {
		return original_title;
	}

	public void setOriginal_title(final String original_title) {
		this.original_title = original_title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(final String overview) {
		this.overview = overview;
	}

	public float getPopularity() {
		return popularity;
	}

	public void setPopularity(final float popularity) {
		this.popularity = popularity;
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(final String poster_path) {
		this.poster_path = poster_path;
	}

	public List<ProductionCompany> getProduction_companies() {
		return production_companies;
	}

	public void setProduction_companies(
			final List<ProductionCompany> production_companies) {
		this.production_companies = production_companies;
	}

	public List<ProductionCountry> getProduction_countries() {
		return production_countries;
	}

	public void setProduction_countries(
			final List<ProductionCountry> production_countries) {
		this.production_countries = production_countries;
	}

	public String getPage() {
		return page;
	}

	public void setPage(final String page) {
		this.page = page;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(final Date release_date) {
		this.release_date = release_date;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(final int revenue) {
		this.revenue = revenue;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(final int runtime) {
		this.runtime = runtime;
	}

	public List<Language> getSpoken_languages() {
		return spoken_languages;
	}

	public void setSpoken_languages(final List<Language> spoken_languages) {
		this.spoken_languages = spoken_languages;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(final String tagline) {
		this.tagline = tagline;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public float getVote_average() {
		return vote_average;
	}

	public void setVote_average(final float vote_average) {
		this.vote_average = vote_average;
	}

	public int getVote_count() {
		return vote_count;
	}

	public void setVote_count(final int vote_count) {
		this.vote_count = vote_count;
	}

	public Cast getCasts() {
		return casts;
	}

	public void setCasts(final Cast casts) {
		this.casts = casts;
	}

	public Trailers getTrailers() {
		return trailers;
	}

	public void setTrailers(final Trailers trailers) {
		this.trailers = trailers;
	}

}
