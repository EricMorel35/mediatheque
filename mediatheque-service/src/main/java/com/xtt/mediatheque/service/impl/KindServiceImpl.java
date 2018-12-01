package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtt.mediatheque.dao.KindDAO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.factory.KindDTOFactory;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.service.KindService;

/**
 * Implementation of {@link KindService}
 * 
 * @author Eric Morel
 */
@Service
public class KindServiceImpl implements KindService {

	private KindDAO kindDAO;
	private KindDTOFactory dtoFactory;

	@Autowired
	public KindServiceImpl(KindDAO kindDAO, KindDTOFactory dtoFactory) {
		this.kindDAO = kindDAO;
		this.dtoFactory = dtoFactory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<KindsDTO> getKinds() throws TechnicalAccessException {
		List<KindsDTO> dto = new ArrayList<>();
		List<MovieKindsEntity> kinds = kindDAO.findAll();
		kinds.stream().forEach(kind -> dto.add(dtoFactory.buildKindsDTO(kind)));
		return dto;
	}

}
