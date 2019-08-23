package com.xtt.mediatheque.tmdb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Trailers {

	@JsonProperty("youtube")
	private List<Youtube> youtube;

	@JsonProperty("quicktime")
	private List<Youtube> quicktime;

	public List<Youtube> getYoutube() {
		return youtube;
	}

	public void setYoutube(final List<Youtube> youtube) {
		this.youtube = youtube;
	}

	public List<Youtube> getQuicktime() {
		return quicktime;
	}

	public void setQuicktime(final List<Youtube> quicktime) {
		this.quicktime = quicktime;
	}

}
