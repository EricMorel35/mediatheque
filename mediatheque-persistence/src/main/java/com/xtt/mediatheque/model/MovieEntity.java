package com.xtt.mediatheque.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "allocine_films")
@NamedQueries(value = {
		@NamedQuery(name = "findMovieById", query = "from MovieEntity m where m.idAllocine = ?"),
		@NamedQuery(name = "findMovieByName", query = "from MovieEntity m where m.movieTitle = ?") })
public class MovieEntity {
	@Id
	@Column(name = "ID")
	private Integer idAllocine;

	@Column(name = "Synopsis")
	private String synopsis;

	@Column(name = "AnneeSortie")
	private Integer releaseYear;

	@Column(name = "urlPoster")
	private String urlCover;

	@Column(name = "Titrefilm")
	private String movieTitle;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieActorsEntity> actors;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieDirectorsEntity> directors;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieCountryEntity> countries;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieKindsEntity> kinds;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "idAllocine", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieUserEntity> movies;

	@Column(name = "urlYoutube")
	private String urlYoutube;

	public List<MovieActorsEntity> getActors() {
		return actors;
	}

	public List<MovieCountryEntity> getCountries() {
		return countries;
	}

	public List<MovieDirectorsEntity> getDirectors() {
		return directors;
	}

	public Integer getIdAllocine() {
		return idAllocine;
	}

	public Integer getIdBackend() {
		return idAllocine;
	}

	public List<MovieKindsEntity> getKinds() {
		return kinds;
	}

	public List<MovieUserEntity> getMovies() {
		return movies;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public String getUrlCover() {
		return urlCover;
	}

	public String getUrlYoutube() {
		return urlYoutube;
	}

	public void setActors(final List<MovieActorsEntity> actors) {
		this.actors = actors;
	}

	public void setCountries(final List<MovieCountryEntity> countries) {
		this.countries = countries;
	}

	public void setDirectors(final List<MovieDirectorsEntity> directors) {
		this.directors = directors;
	}

	public void setIdAllocine(final Integer idAllocine) {
		this.idAllocine = idAllocine;
	}

	public void setKinds(final List<MovieKindsEntity> kinds) {
		this.kinds = kinds;
	}

	public void setMovies(final List<MovieUserEntity> movies) {
		this.movies = movies;
	}

	public void setMovieTitle(final String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public void setReleaseYear(final Integer releaseYear) {
		this.releaseYear = releaseYear;
	}

	public void setSynopsis(final String synopsis) {
		this.synopsis = synopsis;
	}

	public void setUrlCover(final String urlCover) {
		this.urlCover = urlCover;
	}

	public void setUrlYoutube(final String urlYoutube) {
		this.urlYoutube = urlYoutube;
	}

}
