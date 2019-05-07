package com.xtt.mediatheque.dto.factory.impl;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.dto.factory.MovieDTOFactory;
import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentMovieDTO buildFullMovieDTO(final MovieItem movieEntityItem) {
		ContentMovieDTO movieDTO = new ContentMovieDTO();
		movieDTO.setMovieName(movieEntityItem.getMovieName());
		movieDTO.setReleaseYear(movieEntityItem.getReleaseYear());
		movieDTO.setSynopsis(movieEntityItem.getSynopsis());
		movieDTO.setActors(movieEntityItem.getActors().stream().map(ActorsItem::getName).collect(Collectors.toList()));
		movieDTO.setDirectors(movieEntityItem.getDirectors().stream().map(DirectorsItem::getName).collect(Collectors.toList()));
//		movieDTO.setCountries(movieEntityItem.getCountries().stream().map(CountrItem::getName).collect(Collectors.toList()));
		movieDTO.setGenres(movieEntityItem.getGenres().stream().map(KindItem::getName).collect(Collectors.toList()));
//		movieDTO.setCreationDate(movieEntityItem.getTimestampCreationDate().getTime());
		movieDTO.setUrlYoutube(movieEntityItem.getURLYoutube());
		return movieDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CatalogItemDTO buildLightMovieDTO(final MovieUserEntity movieEntity) {
		return CatalogItemDTO.builder().title(movieEntity.getMovieName()).addingDate(new Date())
				.id(movieEntity.getMovie().getMovieId()).build();
	}

}
