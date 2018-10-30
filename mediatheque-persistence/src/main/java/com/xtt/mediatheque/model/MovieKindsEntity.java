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
@Table(name = MovieKindsEntity.TABLE_NAME)
@Data
public class MovieKindsEntity {
	
	public static final String TABLE_NAME = "MovieKind";

	@Embeddable
	@Data
	public static class KindsEmbeddableEntity implements Serializable {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;

		@Column(name = "kind")
		private String kind;

		@ManyToOne
		@JoinColumn(name = "movie_id")
		private MovieEntity idBackend;

	}

	@EmbeddedId
	private KindsEmbeddableEntity pk;

	public KindsEmbeddableEntity getPk() {
		return pk;
	}

	public void setPk(final KindsEmbeddableEntity pk) {
		this.pk = pk;
	}

}