package com.xtt.mediatheque.tmdb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cast {

	@JsonProperty("cast")
	private List<PersonCast> cast;
	@JsonProperty("crew")
	private List<PersonCrew> crew;

	public List<PersonCast> getCast() {
		return cast;
	}

	public void setCast(final List<PersonCast> cast) {
		this.cast = cast;
	}

	public List<PersonCrew> getCrew() {
		return crew;
	}

	public void setCrew(final List<PersonCrew> crew) {
		this.crew = crew;
	}

}
