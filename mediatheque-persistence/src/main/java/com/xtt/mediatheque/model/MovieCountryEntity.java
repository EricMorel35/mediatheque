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
@Table(name = MovieCountryEntity.TABLE_NAME)
@Data
public class MovieCountryEntity {
	
	public static final String TABLE_NAME = "MovieCountry";

	@Embeddable
	public static class CountryEmbeddableEntity implements Serializable {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;

		@Column(name = "country")
		private String countryCode;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

		public String getCountry() {
			return countryCode;
		}

		public void setCountryCode(final String countryCode) {
			this.countryCode = countryCode;
		}

		public MovieEntity getIdBackend() {
			return idBackend;
		}

		public void setIdBackend(final MovieEntity idBackend) {
			this.idBackend = idBackend;
		}

	}

	@EmbeddedId
	private CountryEmbeddableEntity countryPk;

}
