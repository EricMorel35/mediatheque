package com.xtt.mediatheque.dto;

import java.util.Date;

public class CatalogItemDTO {

	private String urlCover;

	private String title;

	private String releaseYear;

	private Date addingDate;

	private Integer idAllocine;

	private String userName;

	public String getUrlCover() {
		return urlCover;
	}

	public void setUrlCover(String urlCover) {
		this.urlCover = urlCover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String releaseYear) {
		this.releaseYear = releaseYear;
	}

	public Date getAddingDate() {
		return addingDate;
	}

	public void setAddingDate(Date addingDate) {
		this.addingDate = addingDate;
	}

	public Integer getIdAllocine() {
		return idAllocine;
	}

	public void setIdAllocine(Integer idAllocine) {
		this.idAllocine = idAllocine;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
