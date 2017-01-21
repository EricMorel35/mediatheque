package com.xtt.mediatheque.exceptions;

public class TechnicalAccessException extends Exception {
	/** Serial Version UID. */
	private static final long serialVersionUID = -6699157372548993488L;

	private String code;

	public TechnicalAccessException(final String code, final String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
