package com.xtt.mediatheque.dao.movie.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.xtt.mediatheque.dao.movie.KindDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.model.wrapped.KindsEntityWrapped;

@Repository
@Transactional
public class KindDAOImpl implements KindDAO {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<KindItem> getKinds() throws TechnicalAccessException {
		List<KindItem> moviesList = new ArrayList<KindItem>();
		List<String> list = getSession().getNamedQuery("findAllKinds").list();

		for (String kind : list) {
			moviesList.add(new KindsEntityWrapped(kind));
		}
		return moviesList;
	}

}
