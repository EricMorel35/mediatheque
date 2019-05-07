package com.xtt.mediatheque.dto.factory.impl;

import org.springframework.stereotype.Component;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.factory.KindDTOFactory;
import com.xtt.mediatheque.model.MovieKindsEntity;

@Component
public class KindDTOFactoryImpl implements KindDTOFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KindsDTO buildKindsDTO(MovieKindsEntity movieKindsEntity) {
		KindsDTO dto = new KindsDTO();
		dto.setName(movieKindsEntity.getKindPk().getKind());
		return dto;
	}

}
