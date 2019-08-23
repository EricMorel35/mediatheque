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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * 
 * @author stuart.boston
 */
@JsonRootName("production_country")
public class ProductionCountry implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ProductionCountry.class);
	/*
	 * Properties
	 */
	@JsonProperty("iso_3166_1")
	private String iso_3166_1;
	@JsonProperty("name")
	private String name;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public String getIso_3166_1() {
		return iso_3166_1;
	}

	public String getName() {
		return name;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setIso_3166_1(final String iso_3166_1) {
		this.iso_3166_1 = iso_3166_1;
	}

	public void setName(final String name) {
		this.name = name;
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
		final ProductionCountry other = (ProductionCountry) obj;
		if ((this.iso_3166_1 == null) ? (other.iso_3166_1 != null)
				: !this.iso_3166_1.equals(other.iso_3166_1)) {
			return false;
		}
		if ((this.name == null) ? (other.name != null) : !this.name
				.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash
				+ (this.iso_3166_1 != null ? this.iso_3166_1.hashCode() : 0);
		hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[ProductionCountry=");
		sb.append("[isoCode=").append(iso_3166_1);
		sb.append("],[name=").append(name);
		sb.append("]]");
		return sb.toString();
	}

}
