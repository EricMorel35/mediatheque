package com.xtt.mediatheque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = MovieEntity.TABLE_NAME)
@Data
public class MovieEntity {

	public static final String TABLE_NAME = "Movies";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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

//	@OneToOne
//	@JoinColumn(name = "movie_user_id")
//	private MovieUserEntity movieUser;

//	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
//	private List<MovieActorsEntity> actors;
//
//	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
//	private List<MovieDirectorsEntity> directors;
//
//	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
//	private List<MovieCountryEntity> countries;
//
//	@OneToMany(mappedBy = "pk.idBackend", cascade = { CascadeType.ALL })
//	private List<MovieKindsEntity> kinds;
//
//	@OneToMany(mappedBy = "idBackend", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
//	private List<MovieUserEntity> movies;

}
