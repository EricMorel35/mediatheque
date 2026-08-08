package com.xtt.mediatheque.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = MovieEntity.TABLE_NAME)
@Data
public class MovieEntity {

	public static final String TABLE_NAME = "Movie";

	@Id
	@Column(name = "movie_id")
	private Long movieId;

	@Column(name = "movie_title")
	private String movieTitle;

	private String synopsis;

	@Column(name = "release_year")
	private Integer releaseYear;

	@Column(name = "url_cover")
	private String urlCover;

	@Column(name = "url_youtube")
	private String urlYoutube;

	@OneToOne
	@JoinColumn(name = "movie_user_id")
	private MovieUserEntity movieUser;

	@OneToMany(mappedBy = "actorsPk.idBackend", cascade = { CascadeType.ALL })
	private List<MovieActorsEntity> actors;

	@OneToMany(mappedBy = "directorsPk.idBackend", cascade = { CascadeType.ALL })
	private List<MovieDirectorsEntity> directors;

	@OneToMany(mappedBy = "countryPk.idBackend", cascade = { CascadeType.ALL })
	private List<MovieCountryEntity> countries;

	@OneToMany(mappedBy = "kindPk.idBackend", cascade = { CascadeType.ALL })
	private List<MovieKindsEntity> kinds;

}
