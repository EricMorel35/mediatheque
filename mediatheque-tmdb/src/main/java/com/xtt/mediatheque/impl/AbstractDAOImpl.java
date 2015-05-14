package com.xtt.mediatheque.impl;

import com.xtt.mediatheque.constants.MediathequeConstants;
import com.xtt.mediatheque.exceptions.MessageException;
import com.xtt.mediatheque.messages.MessageUtils;

public class AbstractDAOImpl {

	private MessageUtils messages;

	protected String buildSearchUrl(final String query) throws MessageException {
		return messages.getMessageWithParameters(MediathequeConstants.SEARCH_TMDB_KEY, new String[] {query});
	}

	protected String buildMovieUrl(final String query) throws MessageException {
		return messages.getMessageWithParameters(MediathequeConstants.MOVIE_TMDB_KEY, new String[] {query});
	}

	public void setMessages(MessageUtils messages) {
		this.messages = messages;
	}

}
