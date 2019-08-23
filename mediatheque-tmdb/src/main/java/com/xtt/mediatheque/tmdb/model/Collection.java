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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * 
 * @author stuart.boston
 */
@JsonRootName("collection")
public class Collection implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Collection.class);
	/*
	 * Properties
	 */
	@JsonProperty("id")
	private int id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("name")
	private String name;
	@JsonProperty("poster_path")
	private String poster_path;
	@JsonProperty("backdrop_path")
	private String backdrop_path;
	@JsonProperty("release_date")
	private String releaseDate;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">

	public int getId() {
		return id;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getTitle() {
		if (StringUtils.isBlank(title)) {
			return name;
		}
		return title;
	}

	public String getName() {
		if (StringUtils.isBlank(name)) {
			return title;
		}
		return name;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">

	public void setId(final int id) {
		this.id = id;
	}

	public void setReleaseDate(final String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setTitle(final String title) {
		this.title = title;
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
		final Collection other = (Collection) obj;
		if ((this.backdrop_path == null) ? (other.backdrop_path != null)
				: !this.backdrop_path.equals(other.backdrop_path)) {
			return false;
		}
		if (this.id != other.id) {
			return false;
		}
		if ((this.title == null) ? (other.title != null) : !this.title
				.equals(other.title)) {
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
		hash = 19
				* hash
				+ (this.backdrop_path != null ? this.backdrop_path.hashCode()
						: 0);
		hash = 19 * hash + this.id;
		hash = 19 * hash + (this.title != null ? this.title.hashCode() : 0);
		hash = 19 * hash + (this.name != null ? this.name.hashCode() : 0);
		hash = 19 * hash
				+ (this.poster_path != null ? this.poster_path.hashCode() : 0);
		hash = 19 * hash
				+ (this.releaseDate != null ? this.releaseDate.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[Collection=");
		sb.append("[id=").append(id);
		sb.append("],[title=").append(title);
		sb.append("],[name=").append(name);
		sb.append("],[posterPath=").append(poster_path);
		sb.append("],[backdropPath=").append(backdrop_path);
		sb.append("],[releaseDate=").append(releaseDate);
		sb.append("]]");
		return sb.toString();
	}

	public String getPoster_path() {
		return poster_path;
	}

	public void setPoster_path(final String poster_path) {
		this.poster_path = poster_path;
	}

	public String getBackdrop_path() {
		return backdrop_path;
	}

	public void setBackdrop_path(final String backdrop_path) {
		this.backdrop_path = backdrop_path;
	}

}
