package com.xtt.mediatheque.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.PersistenceDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;

@Repository
@Transactional
public class PersistenceDAOImpl implements PersistenceDAO {

	private String urlCover;

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCoverByNameFromDisk(final String name) {
		// from MovieUserEntity m where m.movieName like ?
		/*
		 * String query = "from MovieUserEntity m where m.movieName like :name";
		 * List<MovieUserEntity> entities = getHibernateTemplate()
		 * .findByNamedParam(query, "name", '%' + name + '%');
		 */

		// List<MovieEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findMovieByName", name);
		//
		// if (!entities.isEmpty()) {
		// return entities.get(0).getUrlCover();
		// } else {
		// return null;
		// }
		return null;
	}

	@Override
	public void persistMovie(String movieName) throws TechnicalAccessException {
		// MovieUserEntity entity = new MovieUserEntity();
		// entity.setOriginalName(movieName);
		// entity.setCreationDate(new Date());
		//
		// List<MovieEntity> list = this.findMovieById(0);
		// MovieEntity movieEntity = null;
		// if (list.size() > 0) {
		// movieEntity = list.get(0);
		// } else {
		// movieEntity = new MovieEntity();
		// movieEntity.setBackendId(0);
		// getSession().save(movieEntity);
		// }
		//
		// entity.setIdAllocine(movieEntity);
		// List<SupportEntity> supportList = this.findSupportByName("NAS");
		// SupportEntity support = null;
		// if (supportList.size() > 0) {
		// support = supportList.get(0);
		// } else {
		// support = new SupportEntity();
		// support.setMedia("NAS");
		// getSession().save(support);
		// }
		// entity.setSupport(support);
		//
		// getSession().save(entity);
	}

	public void setUrlCover(final String urlCover) {
		this.urlCover = urlCover;
	}

}
