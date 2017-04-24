package com.xtt.mediatheque.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xtt.mediatheque.dao.movie.KindDAO;
import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.dto.factory.KindDTOFactory;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.service.KindService;

@Service
public class KindServiceImpl implements KindService {

	@Autowired
	private KindDAO kindDAO;

	@Autowired
	private KindDTOFactory dtoFactory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<KindsDTO> getKinds() throws TechnicalAccessException {
		List<KindsDTO> dto = new ArrayList<KindsDTO>();
		List<KindItem> kinds = kindDAO.getKinds();
		for (KindItem kind : kinds) {
			dto.add(dtoFactory.buildKindsDTO(kind));
		}
		return dto;
	}

}
