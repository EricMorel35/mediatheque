package com.xtt.mediatheque.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "allocine_directors")
public class MovieDirectorsEntity {

	@Embeddable
	public static class DirectorsEmbeddableEntity implements Serializable {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;

		@Column(name = "director")
		private String director;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

		public String getDirector() {
			return director;
		}

		public void setDirector(final String director) {
			this.director = director;
		}

		public MovieEntity getIdBackend() {
			return idBackend;
		}

		public void setIdBackend(final MovieEntity idBackend) {
			this.idBackend = idBackend;
		}

	}

	@EmbeddedId
	private DirectorsEmbeddableEntity pk;

	public DirectorsEmbeddableEntity getPk() {
		return pk;
	}

	public void setPk(final DirectorsEmbeddableEntity pk) {
		this.pk = pk;
	}

}