package com.xtt.mediatheque.model;

import java.util.List;

public class MoviesList {

	private int page;
	private List<Movie> results;
	private int total_pages;
	private int total_results;

	public int getPage() {
		return page;
	}

	public void setPage(final int page) {
		this.page = page;
	}

	public List<Movie> getResults() {
		return results;
	}

	public void setResults(final List<Movie> results) {
		this.results = results;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(final int total_pages) {
		this.total_pages = total_pages;
	}

	public int getTotal_results() {
		return total_results;
	}

	public void setTotal_results(final int total_results) {
		this.total_results = total_results;
	}

}
