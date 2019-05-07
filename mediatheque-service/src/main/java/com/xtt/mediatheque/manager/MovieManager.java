package com.xtt.mediatheque.manager;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtt.mediatheque.dao.ActorDAO;
import com.xtt.mediatheque.dao.CountryDAO;
import com.xtt.mediatheque.dao.DirectorDAO;
import com.xtt.mediatheque.dao.KindDAO;
import com.xtt.mediatheque.dao.movie.MovieDAO;
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

@Service
public class MovieManager {

	@Autowired
	private MovieDAO movieDAO;

	@Autowired
	private KindDAO kindDAO;
	
	@Autowired
	private ActorDAO actorDAO;
	
	@Autowired
	private DirectorDAO directorDAO;
	
	@Autowired
	private CountryDAO countryDAO;

	public void updateFullDatas(Optional<MovieEntity> optMovie, MovieItem movieItem) {
		optMovie.ifPresent(movie -> {
			movie.setUrlYoutube(movieItem.getURLYoutube());
			movie.setUrlCover(movieItem.getURLPoster());
			movie.setSynopsis(movieItem.getSynopsis().substring(0, 255));
			movieItem.getGenres().forEach(genre -> {
				MovieKindsEntity mke = new MovieKindsEntity();
				KindsEmbeddableEntity pk = new KindsEmbeddableEntity();
				pk.setKind(genre.getName());
				pk.setIdBackend(movie);
				mke.setKindPk(pk);
				kindDAO.save(mke);
			});
			
			movieItem.getActors().forEach(actor -> {
				MovieActorsEntity mae = new MovieActorsEntity();
				ActorsEmbeddableEntity pk = new ActorsEmbeddableEntity();
				pk.setActor(actor.getName());
				pk.setIdBackend(movie);
				mae.setActorsPk(pk);
				actorDAO.save(mae);
			});
			
			movieItem.getDirectors().forEach(director -> {
				MovieDirectorsEntity mde = new MovieDirectorsEntity();
				DirectorsEmbeddableEntity pk = new DirectorsEmbeddableEntity();
				pk.setDirector(director.getName());
				pk.setIdBackend(movie);
				mde.setDirectorsPk(pk);
				directorDAO.save(mde);
			});
			
			movieItem.getCountries().forEach(country -> {
				MovieCountryEntity mce = new MovieCountryEntity();
				CountryEmbeddableEntity pk = new CountryEmbeddableEntity();
				pk.setCountryCode(country.getName());
				pk.setIdBackend(movie);
				mce.setCountryPk(pk);
				countryDAO.save(mce);
			});


			movieDAO.save(movie);
		});
	}

}
