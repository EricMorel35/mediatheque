package com.xtt.mediatheque.dto.factory.impl;

import java.util.Date;

import org.springframework.stereotype.Component;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

/**
 * Implementation of {@link MovieDTOFactory}
 */
@Component
public class MovieDTOFactoryImpl implements MovieDTOFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SearchItemDTO buildLightMovieDTO(MovieItem movieItem) {
		SearchItemDTO dto = new SearchItemDTO();
		dto.setReleaseYear(movieItem.getReleaseYear());
		dto.setTitle(movieItem.getMovieName());
		dto.setUrlCover(movieItem.getURLPoster());
		return dto;
	}

	@Override
	public ContentMovieDTO buildFullMovieDTO(final MovieUserEntityItem movieEntityItem) {
		ContentMovieDTO movieDTO = new ContentMovieDTO();
//		movieDTO.setMovieName(movieEntityItem.getMovieTitle());
//		movieDTO.setReleaseYear(movieEntityItem.getReleaseYear());
//		movieDTO.setSynopsis(movieEntityItem.getSynopsis());
//		movieDTO.setActors(movieEntityItem.getActors());
//		movieDTO.setDirectors(movieEntityItem.getDirectors());
//		if (!StringUtils.isEmpty(movieEntityItem.getUrlPoster())) {
//			String title = movieEntityItem.getMovieTitle().replaceAll(" ", "%20");
//			title = title.replace('?', '/');
//			StringBuffer url = new StringBuffer();
//			String urlCover = url.append("/mediatheque-webapp/movie/cover/").append(title).append(".do").toString();
//			movieDTO.setPoster(urlCover);
//		} else {
//			movieDTO.setPoster("/mediatheque-webapp/images/mistery.png");
//		}
//		movieDTO.setCountries(movieEntityItem.getCountries());
//		movieDTO.setGenres(movieEntityItem.getGenres());
//		movieDTO.setUserName(movieEntityItem.getUserName());
//		movieDTO.setCreationDate(movieEntityItem.getTimestampCreationDate().getTime());
//		movieDTO.setUrlYoutube(movieEntityItem.getURLYoutube());
//		movieDTO.setMedia(movieEntityItem.getSupport());
		return movieDTO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xtt.mediatheque.dto.factory.MovieDTOFactory#buildLightMovieDTO(com.xtt.
	 * mediatheque.model.MovieEntity)
	 */
	@Override
	public CatalogItemDTO buildLightMovieDTO(final MovieUserEntity movieEntity) {
		CatalogItemDTO dto = CatalogItemDTO.builder().title(movieEntity.getMovieName()).addingDate(new Date())
				.id(movieEntity.getId()).build();
		
//		if (!StringUtils.isEmpty(movieEntity.getIdBackend().getUrlCover())) {
//			String title = movieEntity.getMovieName().replaceAll(" ", "%20");
//			title = title.replace('?', '/');
//			StringBuffer url = new StringBuffer();
//			String urlCover = url.append("/mediatheque-webapp/movie/cover/").append(title).append(".do").toString();
//			dto.setUrlCover(urlCover);
//		} else {
//			dto.setUrlCover("/mediatheque-webapp/images/mistery.png");
//		}
		return dto;
	}

}
