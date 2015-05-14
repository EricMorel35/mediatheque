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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Stuart
 */
public class KeywordMovie implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(KeywordMovie.class);
	/*
	 * Properties
	 */
	@JsonProperty("id")
	private String id;
	@JsonProperty("backdrop_path")
	private String backdropPath;
	@JsonProperty("original_title")
	private String originalTitle;
	@JsonProperty("release_date")
	private String releaseDate;
	@JsonProperty("poster_path")
	private String posterPath;
	@JsonProperty("title")
	private String title;
	@JsonProperty("vote_average")
	private float voteAverage;
	@JsonProperty("vote_count")
	private double voteCount;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public String getId() {
		return id;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public String getTitle() {
		return title;
	}

	public float getVoteAverage() {
		return voteAverage;
	}

	public double getVoteCount() {
		return voteCount;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setBackdropPath(final String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setOriginalTitle(final String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public void setReleaseDate(final String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setPosterPath(final String posterPath) {
		this.posterPath = posterPath;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setVoteAverage(final float voteAverage) {
		this.voteAverage = voteAverage;
	}

	public void setVoteCount(final double voteCount) {
		this.voteCount = voteCount;
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

}
