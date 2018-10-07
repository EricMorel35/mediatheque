package com.xtt.mediatheque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = MovieUserEntity.TABLE_NAME)
@Data
public class MovieUserEntity {

	public static final String TABLE_NAME = "Movies_User";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "movie_name")
	private String movieName;

	@Column(name = "original_name")
	private String originalName;

//	@Temporal(TemporalType.DATE)
//	private Date creationDate;

//	@OneToOne(mappedBy = "movieUser", optional = true)
//	private MovieEntity movie;
	private Long movie;

}
