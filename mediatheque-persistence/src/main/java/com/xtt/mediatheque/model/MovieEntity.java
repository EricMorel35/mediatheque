package com.xtt.mediatheque.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

@Entity
@Table(name = MovieEntity.TABLE_NAME)
@NamedQueries(value = { @NamedQuery(name = "findMovieById", query = "from  MovieEntity m where m.backendId = :id"),
		@NamedQuery(name = "findMovieByName", query = "from MovieEntity m where  m.movieTitle = ?") })
@Data
public class MovieEntity {
	
	public static final String TABLE_NAME = "Movies";
	
	@Id
	private long id;

	private String synopsis;

	private Integer releaseYear;

	private String urlCover;

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
	@OneToMany(mappedBy = "idBackend", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	@NotFound(action = NotFoundAction.IGNORE)
	private List<MovieUserEntity> movies;

	private String urlYoutube;

}
