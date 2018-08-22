package com.xtt.mediatheque.model;

import java.io.Serializable;

//@Embeddable
public class CountryEmbeddableEntity implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = -4103133947273054335L;

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
