package com.xtt.mediatheque.dao.movie.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.xtt.mediatheque.model.MovieSearchItem;
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
	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	private MovieUserEntity getMovie(final String movieName) {
		Query query = getSession().getNamedQuery("findByName");
		query.setString(0, StringUtils.upperCase(movieName));
		List<MovieUserEntity> entities = query.list();
		if (!entities.isEmpty()) {
			return entities.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateDatasMovie(final MovieUserEntityItem item, final MovieSearchItem movieItem) {
		Query query = getSession().getNamedQuery("findMovieByName");
		query.setString(0, movieItem.getMovieName());

		List<MovieEntity> entities = query.list();
		MovieUserEntity movie = this.getMovie(item.getMovieName());
		if (entities.size() == 0) {
			MovieEntity movieEntity = new MovieEntity();
			movieEntity.setBackendId(Integer.valueOf(movieItem.getIdBackend()));

			if (StringUtils.isNotEmpty(movieItem.getTitle())) {
				movieEntity.setMovieTitle(movieItem.getTitle());
			} else {
				movieEntity.setMovieTitle(movieItem.getOriginalTitle());
			}
			if (StringUtils.isNotEmpty(movieItem.getReleaseYear())) {
				movieEntity.setReleaseYear(Integer.valueOf(movieItem.getReleaseYear()));
			} else {
				movieEntity.setReleaseYear(0);
			}

			if (StringUtils.isNotEmpty(movieItem.getURLPoster())) {
				File directory = new File("/data/movies/covers");
				if (!directory.exists()) {
					directory.mkdir();
				}

				String remoteUrlCover = new StringBuffer().append("").append(movieItem.getURLPoster()).toString();

				URL url;
				File file = null;
				try {
					url = new URL(remoteUrlCover);
					BufferedImage img = ImageIO.read(url);
					file = new File(new StringBuffer().append(directory.getAbsolutePath())
							.append(movieItem.getURLPoster()).toString());
					ImageIO.write(img, "jpg", file);
				} catch (MalformedURLException e) {
					// TODO Log.
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (file != null) {
					movieEntity.setUrlCover(file.toString());
				} else {
					movieEntity.setUrlCover(StringUtils.EMPTY);
				}
			}
			movieEntity.setCountries(new ArrayList<MovieCountryEntity>());
			movieEntity.setActors(new ArrayList<MovieActorsEntity>());
			movieEntity.setDirectors(new ArrayList<MovieDirectorsEntity>());
			movieEntity.setKinds(new ArrayList<MovieKindsEntity>());
			movieEntity.setUrlYoutube(StringUtils.EMPTY);

			movieEntity.setSynopsis(StringUtils.EMPTY);
			movie.setIdBackend(movieEntity);
			movie.setMovieName(movieItem.getMovieName());
			getSession().save(movieEntity);
		}
		getSession().update(movie);
	}

	private List<MovieEntity> findMovieById(final Integer id) {
		List<MovieEntity> entities = null;
		Query query = getSession().getNamedQuery("findMovieById").setInteger("id", id);
		entities = query.list();
		return entities;
	}

	@Override
	public void updateIdBackend(final MovieUserEntityItem item) {
		List<MovieEntity> entities = findMovieById(-1);
		MovieUserEntity movie = this.getMovie(item.getMovieName());
		if (entities.size() == 0) {
			MovieEntity movieEntity = new MovieEntity();
			movieEntity.setBackendId(-1L);
			movieEntity.setSynopsis(StringUtils.EMPTY);
			movieEntity.setActors(new ArrayList<MovieActorsEntity>());
			movieEntity.setCountries(new ArrayList<MovieCountryEntity>());
			movieEntity.setKinds(new ArrayList<MovieKindsEntity>());
			movieEntity.setDirectors(new ArrayList<MovieDirectorsEntity>());
			movieEntity.setMovieTitle(StringUtils.EMPTY);
			movieEntity.setUrlCover(StringUtils.EMPTY);
			movieEntity.setReleaseYear(0);
			movie.setIdBackend(movieEntity);
			getSession().save(movieEntity);
		} else {
			movie.setIdBackend(entities.get(0));
		}
		getSession().update(movie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovieUserEntityItem> getMoviesByKind(String kind) throws TechnicalAccessException {
		List<MovieUserEntityItem> moviesList = new ArrayList<MovieUserEntityItem>();
		List<Object[]> superObjectLists = getSession().getNamedQuery("findByKind").setString(0, kind).list();
		for (Object[] objectList : superObjectLists) {
			for (Object obj : Arrays.asList(objectList)) {
				if (obj instanceof MovieUserEntity) {
					MovieUserEntity entity = ((MovieUserEntity) obj);
					moviesList.add(new MovieUserEntityWrapped(entity));
				}
			}
		}
		return moviesList;
	}

}
