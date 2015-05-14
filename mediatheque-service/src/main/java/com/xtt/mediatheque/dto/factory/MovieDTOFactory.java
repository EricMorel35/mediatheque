package com.xtt.mediatheque.dto.factory;

import com.xtt.mediatheque.dto.CatalogItemDTO;
import com.xtt.mediatheque.dto.ContentMovieDTO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.SearchItemDTO;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.MovieItem;
import com.xtt.mediatheque.model.entity.MovieUserEntityItem;

public interface MovieDTOFactory {

	ContentMovieDTO buildFullMovieDTO(MovieUserEntityItem movieEntityItem);

	CatalogItemDTO buildLightMovieDTO(MovieUserEntityItem movieEntity);

	SearchItemDTO buildLightMovieDTO(MovieItem movieEntity);

	KindsDTO buildKindsDTO(KindItem movieEntity);

}
