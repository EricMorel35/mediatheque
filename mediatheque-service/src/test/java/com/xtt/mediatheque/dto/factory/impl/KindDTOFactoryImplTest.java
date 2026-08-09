package com.xtt.mediatheque.dto.factory.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieKindsEntity.KindsEmbeddableEntity;

/**
 * Unit tests for {@link KindDTOFactoryImpl}.
 *
 * @author Eric Morel
 */
class KindDTOFactoryImplTest {

	private KindDTOFactoryImpl factory = new KindDTOFactoryImpl();

	@Test
	void buildKindsDTO_mapsKindName() {
		KindsEmbeddableEntity pk = new KindsEmbeddableEntity();
		pk.setKind("Comedy");
		MovieKindsEntity entity = new MovieKindsEntity();
		entity.setKindPk(pk);

		KindsDTO dto = factory.buildKindsDTO(entity);

		assertThat(dto.getName()).isEqualTo("Comedy");
	}

}
