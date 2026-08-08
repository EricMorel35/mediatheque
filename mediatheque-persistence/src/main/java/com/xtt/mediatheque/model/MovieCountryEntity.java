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
@Table(name = MovieCountryEntity.TABLE_NAME)
@Data
public class MovieCountryEntity {

	public static final String TABLE_NAME = "MovieCountry";

	@Embeddable
	@Data
	public static class CountryEmbeddableEntity implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "country")
		private String countryCode;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

	}

	@EmbeddedId
	private CountryEmbeddableEntity countryPk;

}
