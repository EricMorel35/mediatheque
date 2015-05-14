package com.xtt.mediatheque.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Youtube {

	@JsonProperty("name")
	private String name;
	@JsonProperty("size")
	private String size;
	@JsonProperty("source")
	private String source;
	@JsonProperty("type")
	private String type;

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public String getSource() {
		return source;
	}

	public String getType() {
		return type;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setSize(final String size) {
		this.size = size;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public void setType(final String type) {
		this.type = type;
	}

}
