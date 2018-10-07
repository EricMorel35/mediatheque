package com.xtt.mediatheque.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatalogItemDTO {

	private String urlCover;

	private String title;

	private long releaseYear;

	private Date addingDate;

	private long id;

}
