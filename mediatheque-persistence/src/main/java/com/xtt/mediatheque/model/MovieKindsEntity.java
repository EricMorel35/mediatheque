package com.xtt.mediatheque.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "allocine_kinds")
@NamedQueries(value = {
		@NamedQuery(name = "findAllKinds", query = "select distinct(m.pk.kind) from MovieKindsEntity m")})
public class MovieKindsEntity {

	@Embeddable
	public static class KindsEmbeddableEntity implements Serializable {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;

		@Column(name = "kind")
		private String kind;

		@ManyToOne
		@JoinColumn(name = "ID")
		private MovieEntity idBackend;

		public String getKind() {
			return kind;
		}

		public void setKind(final String kind) {
			this.kind = kind;
		}

		public MovieEntity getIdBackend() {
			return idBackend;
		}

		public void setIdBackend(final MovieEntity idBackend) {
			this.idBackend = idBackend;
		}

	}

	@EmbeddedId
	private KindsEmbeddableEntity pk;

	public KindsEmbeddableEntity getPk() {
		return pk;
	}

	public void setPk(final KindsEmbeddableEntity pk) {
		this.pk = pk;
	}

}