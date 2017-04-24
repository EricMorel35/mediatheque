package com.xtt.mediatheque.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "allocine_liste")
@NamedQueries(value = {
		@NamedQuery(name = "findById", query = "from MovieUserEntity m where m.idBackend.backendId = ?"),
		@NamedQuery(name = "findByName", query = "from MovieUserEntity m where UPPER(m.movieName) = ?"),
		@NamedQuery(name = "findByNameWithNoUpper", query = "from MovieUserEntity m  where m.movieName like ?"),
		@NamedQuery(name = "findByKind", query = "from MovieUserEntity m, MovieKindsEntity k where m.idBackend.backendId = k.pk.idBackend.backendId and k.pk.kind = ?") })
public class MovieUserEntity {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "movieName")
	private String movieName;

	@Column(name = "originalName")
	private String originalName;

	@Column(name = "creationDate")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "backendId")
	@NotFound(action = NotFoundAction.IGNORE)
	private MovieEntity idBackend;

	@OneToOne
	@JoinColumn(name = "Media", referencedColumnName = "Id")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private SupportEntity support;

	public Date getCreationDate() {
		return creationDate;
	}

	public int getId() {
		return id;
	}

	public MovieEntity getIdBackend() {
		return idBackend;
	}

	public void setIdBackend(MovieEntity idBackend) {
		this.idBackend = idBackend;
	}

	public String getMovieName() {
		return movieName;
	}

	public SupportEntity getSupport() {
		return support;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setMovieName(final String movieName) {
		this.movieName = movieName;
	}

	public void setSupport(final SupportEntity support) {
		this.support = support;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
}
