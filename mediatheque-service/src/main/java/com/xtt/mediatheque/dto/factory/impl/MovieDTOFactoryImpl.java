package com.xtt.mediatheque.dto.factory.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

@Component
public class MovieDTOFactoryImpl implements MovieDTOFactory {

	@Override
	public ContentMovieDTO buildFullMovieDTO(
			final MovieUserEntityItem movieEntityItem) {
		ContentMovieDTO movieDTO = new ContentMovieDTO();
		movieDTO.setMovieName(movieEntityItem.getMovieTitle());
		movieDTO.setReleaseYear(movieEntityItem.getReleaseYear());
		movieDTO.setSynopsis(movieEntityItem.getSynopsis());
		movieDTO.setActors(movieEntityItem.getActors());
		movieDTO.setDirectors(movieEntityItem.getDirectors());
		if (StringUtils.isNotBlank(movieEntityItem.getUrlPoster())) {
			String title = movieEntityItem.getMovieTitle().replaceAll(" ",
					"%20");
			title = title.replace('?', '/');
			StringBuffer url = new StringBuffer();
			String urlCover = url.append("/mediatheque-webapp/movie/cover/")
					.append(title).append(".do").toString();
			movieDTO.setPoster(urlCover);
		} else {
			movieDTO.setPoster("/mediatheque-webapp/images/mistery.png");
		}
		movieDTO.setCountries(movieEntityItem.getCountries());
		movieDTO.setGenres(movieEntityItem.getGenres());
		movieDTO.setUserName(movieEntityItem.getUserName());
		movieDTO.setCreationDate(movieEntityItem.getTimestampCreationDate()
				.getTime());
		movieDTO.setUrlYoutube(movieEntityItem.getURLYoutube());
		movieDTO.setMedia(movieEntityItem.getSupport());
		return movieDTO;
	}

	@Override
	public CatalogItemDTO buildLightMovieDTO(
			final MovieUserEntityItem movieEntity) {
		CatalogItemDTO dto = new CatalogItemDTO();
		if (StringUtils.isNotBlank(movieEntity.getMovieName())) {
			dto.setTitle(movieEntity.getMovieName());
		} else {
			dto.setTitle(movieEntity.getOriginalName());
		}
		dto.setAddingDate(movieEntity.getTimestampCreationDate());
		dto.setIdAllocine(movieEntity.getIdBackend());
		dto.setUserName(movieEntity.getUserName());
		dto.setReleaseYear(movieEntity.getReleaseYear());
		if (StringUtils.isNotBlank(movieEntity.getUrlPoster())) {
			String title = movieEntity.getMovieTitle().replaceAll(" ", "%20");
			title = title.replace('?', '/');
			StringBuffer url = new StringBuffer();
			String urlCover = url.append("/mediatheque-webapp/movie/cover/")
					.append(title).append(".do").toString();
			dto.setUrlCover(urlCover);
		} else {
			dto.setUrlCover("/mediatheque-webapp/images/mistery.png");
		}
		return dto;
	}

	@Override
	public KindsDTO buildKindsDTO(KindItem movieEntity) {
		KindsDTO dto = new KindsDTO();
		dto.setName(movieEntity.getName());
		return dto;
	}

	@Override
	public SearchItemDTO buildLightMovieDTO(MovieItem movieItem) {
		SearchItemDTO dto = new SearchItemDTO();
		dto.setReleaseYear(movieItem.getReleaseYear());
		dto.setTitle(movieItem.getMovieName());
		dto.setUrlCover(movieItem.getURLPoster());
		return dto;
	}

}
