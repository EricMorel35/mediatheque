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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "allocine_liste")
@NamedQueries(value = {
		@NamedQuery(name = "findById", query = "from MovieUserEntity m where m.idAllocine.idAllocine = ?"),
		@NamedQuery(name = "findByName", query = "from MovieUserEntity m where UPPER(m.movieName) = ?"),
		@NamedQuery(name = "findByNameWithNoUpper", query = "from MovieUserEntity m where m.movieName like ?"),
		@NamedQuery(name = "findByUtil", query = "from MovieUserEntity m where m.user.id = ?"),
		@NamedQuery(name = "findByKind", query = "from MovieUserEntity m, MovieKindsEntity k where m.idAllocine.idAllocine = k.pk.idBackend.idAllocine and k.pk.kind = ?")})
public class MovieUserEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "NomClean")
	private String movieName;

	@Column(name = "NomOrigine")
	private String originalName;

	@OneToOne
	@JoinColumn(name = "IDutil", referencedColumnName = "ID")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private UserEntity user;

	@Column(name = "DateCrea")
	@Temporal(TemporalType.DATE)
	private Date creationDate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinColumn(name = "IDallocine")
	@NotFound(action = NotFoundAction.IGNORE)
	private MovieEntity idAllocine;

	@OneToOne
	@JoinColumn(name = "TypeSupport", referencedColumnName = "ID")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	private SupportEntity support;

	public Date getCreationDate() {
		return creationDate;
	}

	public int getId() {
		return id;
	}

	public MovieEntity getIdBackend() {
		return idAllocine;
	}

	public String getMovieName() {
		return movieName;
	}

	public SupportEntity getSupport() {
		return support;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setIdAllocine(final MovieEntity idAllocine) {
		this.idAllocine = idAllocine;
	}

	public void setMovieName(final String movieName) {
		this.movieName = movieName;
	}

	public void setSupport(final SupportEntity support) {
		this.support = support;
	}

	public void setUser(final UserEntity user) {
		this.user = user;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
}
