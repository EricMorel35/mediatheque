package com.xtt.mediatheque.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = MovieActorsEntity.TABLE_NAME)
@Data
public class MovieActorsEntity {

	public static final String TABLE_NAME = "MovieActors";

	@Embeddable
	@Data
	public static class ActorsEmbeddableEntity implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "actor")
		private String actor;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

	}

	@EmbeddedId
	private ActorsEmbeddableEntity actorsPk;

}
