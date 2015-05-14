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
package com.xtt.mediatheque.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Stuart
 */
public class CollectionInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(CollectionInfo.class);
	/*
	 * Properties
	 */
	@JsonProperty("id")
	private int id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("poster_path")
	private String posterPath;
	@JsonProperty("backdrop_path")
	private String backdropPath;
	@JsonProperty("parts")
	private List<Collection> parts = new ArrayList<Collection>();

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public String getBackdropPath() {
		return backdropPath;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Collection> getParts() {
		return parts;
	}

	public String getPosterPath() {
		return posterPath;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setBackdropPath(final String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setParts(final List<Collection> parts) {
		this.parts = parts;
	}

	public void setPosterPath(final String posterPath) {
		this.posterPath = posterPath;
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
	public String toString() {
		StringBuilder sb = new StringBuilder("[CollectionInfo=");
		sb.append("[id=").append(id);
		sb.append("],[name=").append(name);
		sb.append("],[posterPath=").append(posterPath);
		sb.append("],[backdropPath=").append(backdropPath);
		sb.append("],[# of parts=").append(parts.size());
		sb.append("]]");
		return sb.toString();
	}
}
