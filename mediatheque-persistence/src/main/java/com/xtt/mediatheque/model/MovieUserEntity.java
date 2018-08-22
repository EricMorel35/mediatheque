package com.xtt.mediatheque.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.Data;

@Entity
@Table(name = MovieUserEntity.TABLE_NAME)
@Data
@NamedQueries(value = {
		@NamedQuery(name = "findById", query = "from MovieUserEntity m where m.idBackend.backendId = ?"),
		@NamedQuery(name = "findByName", query = "from MovieUserEntity m where UPPER(m.movieName) = ?"),
		@NamedQuery(name = "findByNameWithNoUpper", query = "from MovieUserEntity m  where m.movieName like ?"),
		@NamedQuery(name = "findByKind", query = "from MovieUserEntity m, MovieKindsEntity k where m.idBackend.backendId = k.pk.idBackend.backendId and k.pk.kind = ?") })
public class MovieUserEntity {
	
	public static final String TABLE_NAME = "MovieUser";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String movieName;

	private String originalName;

	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private MovieEntity idBackend;

}
