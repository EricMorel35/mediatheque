package com.xtt.mediatheque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "allocine_media")
@NamedQueries(value = {
		@NamedQuery(name = "findSupportByName", query = "from SupportEntity s where s.media = :media") })
public class SupportEntity {

	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "Media")
	private String media;

	public int getId() {
		return id;
	}

	public String getMedia() {
		return media;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public void setMedia(final String media) {
		this.media = media;
	}

}
