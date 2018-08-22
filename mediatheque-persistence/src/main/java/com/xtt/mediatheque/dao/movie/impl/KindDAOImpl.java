package com.xtt.mediatheque.dao.movie.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.movie.KindDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.KindItem;

@Repository
public class KindDAOImpl implements KindDAO {

	@Override
	public List<KindItem> getKinds() throws TechnicalAccessException {
		List<KindItem> moviesList = new ArrayList<KindItem>();
		return moviesList;
	}

}
