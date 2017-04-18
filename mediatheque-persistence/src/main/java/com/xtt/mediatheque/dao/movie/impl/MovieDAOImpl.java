package com.xtt.mediatheque.dao.movie.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieActorsEntity.ActorsEmbeddableEntity;
import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieCountryEntity.CountryEmbeddableEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity.DirectorsEmbeddableEntity;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieKindsEntity.KindsEmbeddableEntity;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.ProductionCountryItem;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;
import com.xtt.mediatheque.model.wrapped.MovieUserEntityWrapped;

/**
 * Implementation of {@link MovieDAO}
 */
@Repository
@Transactional
public class MovieDAOImpl implements MovieDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<MovieUserEntityItem> getAllMovies() throws TechnicalAccessException {
		List<MovieUserEntityItem> moviesList = new ArrayList<MovieUserEntityItem>();
		List<MovieUserEntity> list = loadAllMovies();
		for (MovieUserEntity movie : list) {
			moviesList.add(new MovieUserEntityWrapped(movie));
		}
		return moviesList;
	}

	/**
	 * {@inheritDoc}
	 */
	private List<MovieUserEntity> loadAllMovies() throws TechnicalAccessException {
		List<MovieUserEntity> list = null;
		try {
			list = getSession().createCriteria(MovieUserEntity.class).list();
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public MovieUserEntityItem getMovieByExternalId(final long movieId) throws TechnicalAccessException {
		Query query = getSession().createQuery("from MovieUserEntity m where m.idBackend.backendId = ?").setLong(0,
				movieId);
		List<MovieUserEntity> list = query.list();
		if (CollectionUtils.isNotEmpty(list)) {
			return new MovieUserEntityWrapped(list.get(0));
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveUserMovie(MovieUserEntity movieUserEntity) throws TechnicalAccessException {
		try {
			getSession().save(movieUserEntity);
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveMovie(MovieEntity movieEntity) throws TechnicalAccessException {
		try {
			getSession().save(movieEntity);
		} catch (HibernateException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateFullDatas(final MovieUserEntityItem item, final MovieItem movieItem)
			throws TechnicalAccessException {
		try {
			// List<MovieEntity> entities =
			// super.getHibernateTemplate().findByNamedQuery("findMovieById",
			// Integer.valueOf(movieItem.getIdBackend()));
			Query query = getSession().createQuery("from MovieEntity m where m.backendId = ?").setInteger(0,
					Integer.valueOf(movieItem.getIdBackend()));
			List<MovieEntity> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				list.get(0).setSynopsis(movieItem.getSynopsis());
				list.get(0).setUrlYoutube(movieItem.getURLYoutube());
				this.insertCountriesToMovie(movieItem.getCountries(), list.get(0));
				this.insertActorsToMovie(movieItem.getActors(), list.get(0));
				this.insertDirectorsToMovie(movieItem.getDirectors(), list.get(0));
				this.insertKindsToMovie(movieItem.getGenres(), list.get(0));
				getSession().saveOrUpdate(list.get(0));
			}
		} catch (DataAccessException e) {
			throw new TechnicalAccessException("DATA_ACCESS_ERROR", e.getMessage());
		}
	}

	private List<MovieActorsEntity> insertActorsToMovie(final List<ActorsItem> listToConvert,
			final MovieEntity movieEntity) {
		if (listToConvert.size() > 0) {
			List<MovieActorsEntity> list = new ArrayList<MovieActorsEntity>();
			for (ActorsItem actor : listToConvert) {
				ActorsEmbeddableEntity pk = new ActorsEmbeddableEntity();
				pk.setActor(actor.getName());
				pk.setIdBackend(movieEntity);

				MovieActorsEntity movieActor = new MovieActorsEntity();
				movieActor.setPk(pk);
				list.add(movieActor);

				getSession().save(movieActor);
			}
			movieEntity.setActors(list);
			return list;
		} else {
			return null;
		}
	}

	private List<MovieCountryEntity> insertCountriesToMovie(final List<ProductionCountryItem> listToConvert,
			final MovieEntity movieEntity) {
		if (listToConvert.size() > 0) {
			List<MovieCountryEntity> list = new ArrayList<MovieCountryEntity>();
			for (ProductionCountryItem country : listToConvert) {
				CountryEmbeddableEntity pk = new CountryEmbeddableEntity();
				pk.setCountryCode(country.getName());
				pk.setIdBackend(movieEntity);

				MovieCountryEntity movieCountry = new MovieCountryEntity();
				movieCountry.setPk(pk);
				list.add(movieCountry);

				getSession().save(movieCountry);
			}
			movieEntity.setCountries(list);
			return list;
		} else {
			return null;
		}
	}

	private List<MovieDirectorsEntity> insertDirectorsToMovie(final List<DirectorsItem> listToConvert,
			final MovieEntity movieEntity) {
		if (listToConvert.size() > 0) {
			List<MovieDirectorsEntity> list = new ArrayList<MovieDirectorsEntity>();
			for (DirectorsItem director : listToConvert) {
				DirectorsEmbeddableEntity pk = new DirectorsEmbeddableEntity();
				pk.setDirector(director.getName());
				pk.setIdBackend(movieEntity);

				MovieDirectorsEntity movieDirector = new MovieDirectorsEntity();
				movieDirector.setPk(pk);
				list.add(movieDirector);

				getSession().save(movieDirector);
			}
			movieEntity.setDirectors(list);
			return list;
		} else {
			return null;
		}
	}

	private List<MovieKindsEntity> insertKindsToMovie(final List<KindItem> listToConvert,
			final MovieEntity movieEntity) {
		if (listToConvert.size() > 0) {
			List<MovieKindsEntity> list = new ArrayList<MovieKindsEntity>();
			for (KindItem kind : listToConvert) {
				KindsEmbeddableEntity pk = new KindsEmbeddableEntity();
				pk.setKind(kind.getName());
				pk.setIdBackend(movieEntity);

				MovieKindsEntity movieKind = new MovieKindsEntity();
				movieKind.setPk(pk);
				list.add(movieKind);

				getSession().save(movieKind);
			}
			movieEntity.setKinds(list);
			return list;
		} else {
			return null;
		}
	}

}
