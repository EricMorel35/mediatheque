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
@Table(name = MovieKindsEntity.TABLE_NAME)
@Data
public class MovieKindsEntity {

	public static final String TABLE_NAME = "MovieKind";

	@Embeddable
	@Data
	public static class KindsEmbeddableEntity implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "kind")
		private String kind;

		@ManyToOne
		@JoinColumn(name = "movie_id")
		private MovieEntity idBackend;

	}

	@EmbeddedId
	private KindsEmbeddableEntity kindPk;

}