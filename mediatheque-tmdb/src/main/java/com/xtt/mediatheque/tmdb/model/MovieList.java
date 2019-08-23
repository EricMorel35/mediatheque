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

/**
 * 
 * @author Stuart
 */
public class MovieList implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MovieList.class);
	/*
	 * Properties
	 */
	@JsonProperty("description")
	private String description;
	@JsonProperty("favorite_count")
	private int favoriteCount;
	@JsonProperty("id")
	private String id;
	@JsonProperty("item_count")
	private int itemCount;
	@JsonProperty("iso_639_1")
	private String language;
	@JsonProperty("name")
	private String name;
	@JsonProperty("poster_path")
	private String posterPath;
	@JsonProperty("list_type")
	private String listType;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public String getDescription() {
		return description;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public String getId() {
		return id;
	}

	public int getItemCount() {
		return itemCount;
	}

	public String getLanguage() {
		return language;
	}

	public String getName() {
		return name;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getListType() {
		return listType;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setDescription(final String description) {
		this.description = description;
	}

	public void setFavoriteCount(final int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setItemCount(final int itemCount) {
		this.itemCount = itemCount;
	}

	public void setLanguage(final String language) {
		this.language = language;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPosterPath(final String posterPath) {
		this.posterPath = posterPath;
	}

	public void setListType(final String listType) {
		this.listType = listType;
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
		return "MovieList{" + "description=" + description + ", favoriteCount="
				+ favoriteCount + ", id=" + id + ", itemCount=" + itemCount
				+ ", language=" + language + ", name=" + name + ", posterPath="
				+ posterPath + '}';
	}
}
