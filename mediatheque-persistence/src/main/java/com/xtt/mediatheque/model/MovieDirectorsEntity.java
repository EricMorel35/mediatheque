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
@Table(name = MovieDirectorsEntity.TABLE_NAME)
@Data
public class MovieDirectorsEntity {

	public static final String TABLE_NAME = "MovieDirector";

	@Embeddable
	@Data
	public static class DirectorsEmbeddableEntity implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "director")
		private String director;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

	}

	@EmbeddedId
	private DirectorsEmbeddableEntity directorsPk;

}