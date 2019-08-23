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
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author stuart.boston
 */
public class TmdbConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Logger
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(TmdbConfiguration.class);
	/*
	 * Properties
	 */
	@JsonProperty("base_url")
	private String baseUrl;
	@JsonProperty("secure_base_url")
	private String secureBaseUrl;
	@JsonProperty("poster_sizes")
	private List<String> posterSizes;
	@JsonProperty("backdrop_sizes")
	private List<String> backdropSizes;
	@JsonProperty("profile_sizes")
	private List<String> profileSizes;
	@JsonProperty("logo_sizes")
	private List<String> logoSizes;

	// <editor-fold defaultstate="collapsed"
	// desc="Getter methods">//GEN-BEGIN:getterMethods
	public List<String> getBackdropSizes() {
		return backdropSizes;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public List<String> getPosterSizes() {
		return posterSizes;
	}

	public List<String> getProfileSizes() {
		return profileSizes;
	}

	public List<String> getLogoSizes() {
		return logoSizes;
	}

	public String getSecureBaseUrl() {
		return secureBaseUrl;
	}

	// </editor-fold>
	// <editor-fold defaultstate="collapsed"
	// desc="Setter methods">//GEN-BEGIN:setterMethods
	public void setBackdropSizes(final List<String> backdropSizes) {
		this.backdropSizes = backdropSizes;
	}

	public void setBaseUrl(final String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setPosterSizes(final List<String> posterSizes) {
		this.posterSizes = posterSizes;
	}

	public void setProfileSizes(final List<String> profileSizes) {
		this.profileSizes = profileSizes;
	}

	public void setLogoSizes(final List<String> logoSizes) {
		this.logoSizes = logoSizes;
	}

	public void setSecureBaseUrl(final String secureBaseUrl) {
		this.secureBaseUrl = secureBaseUrl;
	}

	// </editor-fold>

	/**
	 * Copy the data from the passed object to this one
	 * 
	 * @param config
	 */
	public void clone(final TmdbConfiguration config) {
		backdropSizes = config.getBackdropSizes();
		baseUrl = config.getBaseUrl();
		posterSizes = config.getPosterSizes();
		profileSizes = config.getProfileSizes();
		logoSizes = config.getLogoSizes();
	}

	/**
	 * Check that the poster size is valid
	 * 
	 * @param posterSize
	 */
	public boolean isValidPosterSize(final String posterSize) {
		if (StringUtils.isBlank(posterSize) || posterSizes.isEmpty()) {
			return false;
		}
		return posterSizes.contains(posterSize);
	}

	/**
	 * Check that the backdrop size is valid
	 * 
	 * @param backdropSize
	 */
	public boolean isValidBackdropSize(final String backdropSize) {
		if (StringUtils.isBlank(backdropSize) || backdropSizes.isEmpty()) {
			return false;
		}
		return backdropSizes.contains(backdropSize);
	}

	/**
	 * Check that the profile size is valid
	 * 
	 * @param profileSize
	 */
	public boolean isValidProfileSize(final String profileSize) {
		if (StringUtils.isBlank(profileSize) || profileSizes.isEmpty()) {
			return false;
		}
		return profileSizes.contains(profileSize);
	}

	/**
	 * Check that the logo size is valid
	 * 
	 * @param logoSize
	 */
	public boolean isValidLogoSize(final String logoSize) {
		if (StringUtils.isBlank(logoSize) || logoSizes.isEmpty()) {
			return false;
		}
		return logoSizes.contains(logoSize);
	}

	/**
	 * Check to see if the size is valid for any of the images types
	 * 
	 * @param sizeToCheck
	 */
	public boolean isValidSize(final String sizeToCheck) {
		return (isValidPosterSize(sizeToCheck)
				|| isValidBackdropSize(sizeToCheck)
				|| isValidProfileSize(sizeToCheck) || isValidLogoSize(sizeToCheck));
	}

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
		if (LOG.isTraceEnabled()) {
			LOG.trace(sb.toString());
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[ImageConfiguration=");
		sb.append("[baseUrl=").append(baseUrl);
		sb.append("],[posterSizes=").append(posterSizes.toString());
		sb.append("],[backdropSizes=").append(backdropSizes.toString());
		sb.append("],[profileSizes=").append(profileSizes.toString());
		sb.append("],[logoSizes=").append(logoSizes.toString());
		sb.append(("]]"));
		return sb.toString();
	}
}
