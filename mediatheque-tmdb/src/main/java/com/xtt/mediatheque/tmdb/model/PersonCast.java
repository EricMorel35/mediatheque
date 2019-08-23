/*
 *      Copyright (c) 2004-2013 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.xtt.mediatheque.tmdb.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Stuart
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonCast implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PersonCast.class);
	/*
	 * Properties
	 */
	@JsonProperty("id")
	private int id;
	@JsonProperty("character")
	private String character;
	@JsonProperty("name")
	private String name;
	@JsonProperty("order")
	private int order;
	@JsonProperty("profile_path")
	private String profile_path;
	@JsonProperty("cast_id")
	private int cast_id;
	@JsonProperty("credit_id")
	private String credit_id;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public String getCharacter() {
		return character;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getOrder() {
		return order;
	}

	public String getProfile_path() {
		return profile_path;
	}

	public int getCast_id() {
		return cast_id;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setCharacter(final String character) {
		this.character = character;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setOrder(final int order) {
		this.order = order;
	}

	public void setProfile_path(final String profilePath) {
		this.profile_path = profilePath;
	}

	public void setCast_id(final int castId) {
		this.cast_id = castId;
	}

	public String getCredit_id() {
		return credit_id;
	}

	public void setCredit_id(String credit_id) {
		this.credit_id = credit_id;
	}	

	// </editor-fold>


	/**
	 * Handle unknown properties and print a message
	 * 
	 * @param key
	 * @param value
	 */
	@JsonAnySetter
	public void handleUnknown(final String key, final Object value) {
		StringBuilder sb = new StringBuilder();
		sb.append("Unknown property: '").append(key);
		sb.append("' value: '").append(value).append("'");
		LOG.trace(sb.toString());
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PersonCast other = (PersonCast) obj;
		if (this.id != other.id) {
			return false;
		}
		if ((this.character == null) ? (other.character != null)
				: !this.character.equals(other.character)) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {
			return false;
		}
		if (this.order != other.order) {
			return false;
		}
		if ((this.profile_path == null) ? (other.profile_path != null)
				: !this.profile_path.equals(other.profile_path)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + this.id;
		hash = 41 * hash
				+ (this.character != null ? this.character.hashCode() : 0);
		hash = 41 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 41 * hash + this.order;
		hash = 41
				* hash
				+ (this.profile_path != null ? this.profile_path.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[PersonCast=");
		sb.append("id=").append(id);
		sb.append("],[character=").append(character);
		sb.append("],[name=").append(name);
		sb.append("],[order=").append(order);
		sb.append("],[profilePath=").append(profile_path);
		sb.append("]]");
		return sb.toString();
	}
}
