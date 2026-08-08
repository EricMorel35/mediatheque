package com.xtt.mediatheque.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = MovieUserEntity.TABLE_NAME)
@Data
public class MovieUserEntity {

	public static final String TABLE_NAME = "MovieUser";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "movie_name")
	private String movieName;

	@Column(name = "original_name")
	private String originalName;

	private LocalDate creationDate;

	@OneToOne(mappedBy = "movieUser", cascade = CascadeType.ALL)
	private MovieEntity movie;

}
