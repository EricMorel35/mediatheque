package com.xtt.mediatheque.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.PersistenceDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.ProductionCountryItem;
import com.xtt.mediatheque.model.SupportEntity;
import com.xtt.mediatheque.model.UserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

@Repository
public class PersistenceDAOImpl implements PersistenceDAO {

	private String urlCover;

	@SuppressWarnings("unchecked")
	private List<MovieEntity> findMovieById(final Object id) {
		// List<MovieEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findMovieById", id);
		// return entities;
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<UserEntity> findUserByName(final String userName) {
		// List<UserEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findUserByName", userName);
		// return entities;
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<SupportEntity> findSupportByName(final String support) {
		// List<SupportEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findSupportByName", support);
		// return entities;
		return null;
	}

	@Override
	public List<MovieUserEntityItem> getAllMovies() {
		List<MovieUserEntityItem> moviesList = new ArrayList<MovieUserEntityItem>();
		// List<MovieUserEntity> list = loadAllMovies();
		// for (MovieUserEntity movie : list) {
		// moviesList.add(new MovieUserEntityWrapped(movie));
		// }
		return moviesList;
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

	@SuppressWarnings("unchecked")
	private MovieUserEntity getMovie(final String movieName) {
		// List<MovieUserEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findByName",
		// StringUtils.upperCase(movieName));
		// if (!entities.isEmpty()) {
		// return entities.get(0);
		// } else {
		// return null;
		// }
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public MovieUserEntityItem getMovieById(final Integer movieId)
			throws TechnicalAccessException {
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
		return null;
	}

	private List<MovieActorsEntity> insertActorsToMovie(
			final List<ActorsItem> listToConvert, final MovieEntity movieEntity) {
		// if (listToConvert.size() > 0) {
		// List<MovieActorsEntity> list = new ArrayList<MovieActorsEntity>();
		// for (ActorsItem actor : listToConvert) {
		// ActorsEmbeddableEntity pk = new ActorsEmbeddableEntity();
		// pk.setActor(actor.getName());
		// pk.setIdBackend(movieEntity);
		//
		// MovieActorsEntity movieActor = new MovieActorsEntity();
		// movieActor.setPk(pk);
		// list.add(movieActor);
		//
		// super.getHibernateTemplate().save(movieActor);
		// }
		// movieEntity.setActors(list);
		// return list;
		// } else {
		// return null;
		// }
		return null;
	}

	private List<MovieCountryEntity> insertCountriesToMovie(
			final List<ProductionCountryItem> listToConvert,
			final MovieEntity movieEntity) {
		// if (listToConvert.size() > 0) {
		// List<MovieCountryEntity> list = new ArrayList<MovieCountryEntity>();
		// for (ProductionCountryItem country : listToConvert) {
		// CountryEmbeddableEntity pk = new CountryEmbeddableEntity();
		// pk.setCountryCode(country.getName());
		// pk.setIdBackend(movieEntity);
		//
		// MovieCountryEntity movieCountry = new MovieCountryEntity();
		// movieCountry.setPk(pk);
		// list.add(movieCountry);
		//
		// super.getHibernateTemplate().save(movieCountry);
		// }
		// movieEntity.setCountries(list);
		// return list;
		// } else {
		// return null;
		// }
		return null;
	}

	private List<MovieDirectorsEntity> insertDirectorsToMovie(
			final List<DirectorsItem> listToConvert,
			final MovieEntity movieEntity) {
		// if (listToConvert.size() > 0) {
		// List<MovieDirectorsEntity> list = new
		// ArrayList<MovieDirectorsEntity>();
		// for (DirectorsItem director : listToConvert) {
		// DirectorsEmbeddableEntity pk = new DirectorsEmbeddableEntity();
		// pk.setDirector(director.getName());
		// pk.setIdBackend(movieEntity);
		//
		// MovieDirectorsEntity movieDirector = new MovieDirectorsEntity();
		// movieDirector.setPk(pk);
		// list.add(movieDirector);
		//
		// super.getHibernateTemplate().save(movieDirector);
		// }
		// movieEntity.setDirectors(list);
		// return list;
		// } else {
		// return null;
		// }
		return null;
	}

	private List<MovieKindsEntity> insertKindsToMovie(
			final List<KindItem> listToConvert, final MovieEntity movieEntity) {
		// if (listToConvert.size() > 0) {
		// List<MovieKindsEntity> list = new ArrayList<MovieKindsEntity>();
		// for (KindItem kind : listToConvert) {
		// KindsEmbeddableEntity pk = new KindsEmbeddableEntity();
		// pk.setKind(kind.getName());
		// pk.setIdBackend(movieEntity);
		//
		// MovieKindsEntity movieKind = new MovieKindsEntity();
		// movieKind.setPk(pk);
		// list.add(movieKind);
		//
		// super.getHibernateTemplate().save(movieKind);
		// }
		// movieEntity.setKinds(list);
		// return list;
		// } else {
		// return null;
		// }
		return null;
	}

	private List<MovieUserEntity> loadAllMovies() {
		// List<MovieUserEntity> list = super.getHibernateTemplate().loadAll(
		// MovieUserEntity.class);
		// return list;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateDatasMovie(final MovieUserEntityItem item,
			final MovieSearchItem movieItem) {
		// List<MovieEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findMovieByName", movieItem.getMovieName());
		// MovieUserEntity movie = this.getMovie(item.getMovieName());
		// if (entities.size() == 0) {
		// MovieEntity movieEntity = new MovieEntity();
		// movieEntity
		// .setIdAllocine(Integer.valueOf(movieItem.getIdBackend()));
		//
		// if (StringUtils.isNotEmpty(movieItem.getTitle())) {
		// movieEntity.setMovieTitle(movieItem.getTitle());
		// } else {
		// movieEntity.setMovieTitle(movieItem.getOriginalTitle());
		// }
		// if (StringUtils.isNotEmpty(movieItem.getReleaseYear())) {
		// movieEntity.setReleaseYear(Integer.valueOf(movieItem
		// .getReleaseYear()));
		// } else {
		// movieEntity.setReleaseYear(0);
		// }
		//
		// if (StringUtils.isNotEmpty(movieItem.getURLPoster())) {
		// File directory = new File("/data/movies/covers");
		// if (!directory.exists()) {
		// directory.mkdir();
		// }
		//
		// String remoteUrlCover = new StringBuffer().append(urlCover)
		// .append(movieItem.getURLPoster()).toString();
		//
		// URL url;
		// File file = null;
		// try {
		// url = new URL(remoteUrlCover);
		// BufferedImage img = ImageIO.read(url);
		// file = new File(new StringBuffer()
		// .append(directory.getAbsolutePath())
		// .append(movieItem.getURLPoster()).toString());
		// ImageIO.write(img, "jpg", file);
		// } catch (MalformedURLException e) {
		// // TODO Log.
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		//
		// if (file != null) {
		// movieEntity.setUrlCover(file.toString());
		// } else {
		// movieEntity.setUrlCover(StringUtils.EMPTY);
		// }
		// }
		// movieEntity.setCountries(new ArrayList<MovieCountryEntity>());
		// movieEntity.setActors(new ArrayList<MovieActorsEntity>());
		// movieEntity.setDirectors(new ArrayList<MovieDirectorsEntity>());
		// movieEntity.setKinds(new ArrayList<MovieKindsEntity>());
		// movieEntity.setUrlYoutube(StringUtils.EMPTY);
		//
		// movieEntity.setSynopsis(StringUtils.EMPTY);
		// movie.setIdAllocine(movieEntity);
		// movie.setMovieName(movieItem.getMovieName());
		// super.getHibernateTemplate().save(movieEntity);
		// }
		// super.getHibernateTemplate().update(movie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateFullDatas(final MovieUserEntityItem item,
			final MovieItem movieItem) throws TechnicalAccessException {
		// try {
		// List<MovieEntity> entities = super.getHibernateTemplate()
		// .findByNamedQuery("findMovieById",
		// Integer.valueOf(movieItem.getIdBackend()));
		// if (entities.size() > 0) {
		// entities.get(0).setSynopsis(movieItem.getSynopsis());
		// entities.get(0).setUrlYoutube(movieItem.getURLYoutube());
		// this.insertCountriesToMovie(movieItem.getCountries(),
		// entities.get(0));
		// this.insertActorsToMovie(movieItem.getActors(), entities.get(0));
		// this.insertDirectorsToMovie(movieItem.getDirectors(),
		// entities.get(0));
		// this.insertKindsToMovie(movieItem.getGenres(), entities.get(0));
		// super.getHibernateTemplate().update(entities.get(0));
		// }
		// } catch (DataAccessException e) {
		// throw new TechnicalAccessException(e.getMessage());
		// }
	}

	@Override
	public void updateIdAllocine(final MovieUserEntityItem item) {
		// List<MovieEntity> entities = findMovieById(-1);
		// MovieUserEntity movie = this.getMovie(item.getMovieName());
		// if (entities.size() == 0) {
		// MovieEntity movieEntity = new MovieEntity();
		// movieEntity.setIdAllocine(-1);
		// movieEntity.setSynopsis(StringUtils.EMPTY);
		// movieEntity.setActors(new ArrayList<MovieActorsEntity>());
		// movieEntity.setCountries(new ArrayList<MovieCountryEntity>());
		// movieEntity.setKinds(new ArrayList<MovieKindsEntity>());
		// movieEntity.setDirectors(new ArrayList<MovieDirectorsEntity>());
		// movieEntity.setMovieTitle(StringUtils.EMPTY);
		// movieEntity.setUrlCover(StringUtils.EMPTY);
		// movieEntity.setReleaseYear(0);
		// movie.setIdAllocine(movieEntity);
		// super.getHibernateTemplate().save(movieEntity);
		// } else {
		// movie.setIdAllocine(entities.get(0));
		// }
		// super.getHibernateTemplate().update(movie);
	}

	@Override
	public void persistMovie(String movieName, String userName)
			throws TechnicalAccessException {
		// MovieUserEntity entity = new MovieUserEntity();
		// entity.setMovieName(movieName);
		//
		// List<UserEntity> userList = this.findUserByName(userName);
		// UserEntity user = null;
		// if (userList.size() > 0) {
		// user = userList.get(0);
		// } else {
		// user = new UserEntity();
		// user.setNom(userName);
		// super.getHibernateTemplate().save(user);
		// }
		// entity.setUser(user);
		// entity.setCreationDate(new Date());
		//
		// List<MovieEntity> list = this.findMovieById(0);
		// MovieEntity movieEntity = null;
		// if (list.size() > 0) {
		// movieEntity = list.get(0);
		// } else {
		// movieEntity = new MovieEntity();
		// movieEntity.setIdAllocine(0);
		// super.getHibernateTemplate().save(movieEntity);
		// }
		//
		// entity.setIdAllocine(movieEntity);
		//
		// List<SupportEntity> supportList = this.findSupportByName("NAS");
		// SupportEntity support = null;
		// if (supportList.size() > 0) {
		// support = supportList.get(0);
		// } else {
		// support = new SupportEntity();
		// support.setMedia("NAS");
		// super.getHibernateTemplate().save(support);
		// }
		// entity.setSupport(support);
		//
		// super.getHibernateTemplate().save(entity);
		// super.getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KindItem> getKinds() throws TechnicalAccessException {
		List<KindItem> moviesList = new ArrayList<KindItem>();
		// List<String> list = super.getHibernateTemplate().findByNamedQuery(
		// "findAllKinds");
		// for (String kind : list) {
		// moviesList.add(new KindsEntityWrapped(kind));
		// }
		return moviesList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MovieUserEntityItem> getMoviesByKind(String kind)
			throws TechnicalAccessException {
		// List<MovieUserEntityItem> moviesList = new
		// ArrayList<MovieUserEntityItem>();
		// List<Object[]> superObjectLists = super.getHibernateTemplate()
		// .findByNamedQuery("findByKind", kind);
		// for (Object[] objectList : superObjectLists) {
		// for (Object obj : Arrays.asList(objectList)) {
		// if (obj instanceof MovieUserEntity) {
		// MovieUserEntity entity = ((MovieUserEntity) obj);
		// moviesList.add(new MovieUserEntityWrapped(entity));
		// }
		// }
		// }
		// return moviesList;
		return null;
	}

	public void setUrlCover(final String urlCover) {
		this.urlCover = urlCover;
	}

}
