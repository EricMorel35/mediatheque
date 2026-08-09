package com.xtt.mediatheque.service.impl;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.xtt.mediatheque.dao.KindDAO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.factory.KindDTOFactory;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieKindsEntity.KindsEmbeddableEntity;

/**
 * Unit tests for {@link KindServiceImpl}.
 *
 * @author Eric Morel
 */
@ExtendWith(MockitoExtension.class)
class KindServiceImplTest {

	@Mock
	private KindDAO kindDAO;

	@Mock
	private KindDTOFactory dtoFactory;

	@InjectMocks
	private KindServiceImpl kindService;

	@Test
	void getKinds_mapsEachEntityToADTO() throws TechnicalAccessException {
		MovieKindsEntity action = kindEntity("Action");
		MovieKindsEntity drama = kindEntity("Drama");
		when(kindDAO.findAll()).thenReturn(List.of(action, drama));

		KindsDTO actionDto = new KindsDTO();
		actionDto.setName("Action");
		KindsDTO dramaDto = new KindsDTO();
		dramaDto.setName("Drama");
		when(dtoFactory.buildKindsDTO(action)).thenReturn(actionDto);
		when(dtoFactory.buildKindsDTO(drama)).thenReturn(dramaDto);

		List<KindsDTO> result = kindService.getKinds();

		assertThat(result).containsExactly(actionDto, dramaDto);
	}

	@Test
	void getKinds_whenNoneStored_returnsEmptyList() throws TechnicalAccessException {
		when(kindDAO.findAll()).thenReturn(List.of());

		List<KindsDTO> result = kindService.getKinds();

		assertThat(result).isEmpty();
	}

	private MovieKindsEntity kindEntity(String kind) {
		KindsEmbeddableEntity pk = new KindsEmbeddableEntity();
		pk.setKind(kind);
		MovieKindsEntity entity = new MovieKindsEntity();
		entity.setKindPk(pk);
		return entity;
	}

}
