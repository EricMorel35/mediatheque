package com.xtt.mediatheque.exceptions;

/** 
 * @author Eric Morel
 */
public class MovieNotFoundException extends Exception {

	/** Serial Version UID. */
	private static final long serialVersionUID = -6699157372548993488L;

	public MovieNotFoundException(String message) {
		super(message);
	}

}
