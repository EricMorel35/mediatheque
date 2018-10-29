package com.xtt.mediatheque.dto.factory;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public interface MovieDTOFactory {

	ContentMovieDTO buildFullMovieDTO(MovieItem movieEntityItem);

	CatalogItemDTO buildLightMovieDTO(MovieUserEntity movieEntity);

	SearchItemDTO buildLightMovieDTO(MovieItem movieEntity);

}
