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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenSession {
	/*
	 * Logger
	 */

	private static final Logger LOG = LoggerFactory
			.getLogger(TokenSession.class);
	/*
	 * Properties
	 */
	@JsonProperty("session_id")
	private String sessionId;
	@JsonProperty("success")
	private Boolean success;
	@JsonProperty("status_code")
	private String statusCode;
	@JsonProperty("status_message")
	private String statusMessage;
	@JsonProperty("guest_session_id")
	private String guestSessionId;
	@JsonProperty("expires_at")
	private String expiresAt;

	// <editor-fold defaultstate="collapsed" desc="Getter methods">
	public String getSessionId() {
		return sessionId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public String getGuestSessionId() {
		return guestSessionId;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Setter methods">
	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSuccess(final Boolean success) {
		this.success = success;
	}

	public void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusMessage(final String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public void setGuestSessionId(final String guestSessionId) {
		this.guestSessionId = guestSessionId;
	}

	public void setExpiresAt(final String expiresAt) {
		this.expiresAt = expiresAt;
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
		return "TokenSession{" + "sessionId=" + sessionId + ", success="
				+ success + ", statusCode=" + statusCode + ", statusMessage="
				+ statusMessage + ", guestSessionId=" + guestSessionId
				+ ", expiresAt=" + expiresAt + '}';
	}
}
