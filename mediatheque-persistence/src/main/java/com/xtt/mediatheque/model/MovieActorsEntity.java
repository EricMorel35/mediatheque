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
@Table(name = "allocine_actors")
public class MovieActorsEntity {

	@Embeddable
	public static class ActorsEmbeddableEntity implements Serializable {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;

		@Column(name = "actor")
		private String actor;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

		public String getActor() {
			return actor;
		}

		public void setActor(final String actor) {
			this.actor = actor;
		}

		public MovieEntity getIdBackend() {
			return idBackend;
		}

		public void setIdBackend(final MovieEntity idBackend) {
			this.idBackend = idBackend;
		}

	}

	@EmbeddedId
	private ActorsEmbeddableEntity pk;

	public ActorsEmbeddableEntity getPk() {
		return pk;
	}

	public void setPk(final ActorsEmbeddableEntity pk) {
		this.pk = pk;
	}

}
