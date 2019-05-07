package com.xtt.mediatheque.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = MovieActorsEntity.TABLE_NAME)
@Data
public class MovieActorsEntity {
	
	public static final String TABLE_NAME = "MovieActors";

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
	private ActorsEmbeddableEntity actorsPk;

}
