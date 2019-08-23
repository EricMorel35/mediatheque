package com.xtt.mediatheque.model;

public class CountryEmbeddableEntity {

	private String countryCode;

	private MovieEntity idBackend;

	public String getCountryCode() {
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
