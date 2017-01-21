package com.xtt.mediatheque.dao.movie.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;
import com.xtt.mediatheque.model.wrapped.MovieUserEntityWrapped;

@Repository
@Transactional
public class MovieDAOImpl implements MovieDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<MovieUserEntityItem> getAllMovies() throws TechnicalAccessException {
		List<MovieUserEntityItem> moviesList = new ArrayList<MovieUserEntityItem>();
		List<MovieUserEntity> list = loadAllMovies();
		for (MovieUserEntity movie : list) {
			moviesList.add(new MovieUserEntityWrapped(movie));
		}
		return moviesList;
	}

	private List<MovieUserEntity> loadAllMovies() throws TechnicalAccessException {
		List<MovieUserEntity> list = null;
		try {
			list = getSession().createCriteria(MovieUserEntity.class).list();
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public MovieUserEntityItem getMovieById(final Integer movieId) throws TechnicalAccessException {
		// try {
		// List<MovieUserEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findById", movieId);
		// if (!entities.isEmpty()) {
		// return new MovieUserEntityWrapped(entities.get(0));
		// } else {
		// return null;
		// }
		// } catch (DataAccessException e) {
		// throw new TechnicalAccessException(e.getMessage());
		// }
		MovieUserEntity entity = (MovieUserEntity) getSession().get(MovieUserEntity.class, movieId);
		return new MovieUserEntityWrapped(entity);
	}

	@Override
	public void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException {
		try {
			getSession().save(movieUserEntity);
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
	}

	@Override
	public void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException {
		try {
			getSession().save(movieEntity);
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
	}

}
