package com.xtt.mediatheque.dto.factory.impl;

import org.springframework.stereotype.Component;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.factory.KindDTOFactory;
import com.xtt.mediatheque.model.KindItem;

@Component
public class KindDTOFactoryImpl implements KindDTOFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public KindsDTO buildKindsDTO(KindItem movieEntity) {
		KindsDTO dto = new KindsDTO();
		dto.setName(movieEntity.getName());
		return dto;
	}

}
