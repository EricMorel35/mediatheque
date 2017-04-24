package com.xtt.mediatheque.dao;

import com.xtt.mediatheque.exceptions.TechnicalAccessException;

public interface PersistenceDAO {

	String getCoverByNameFromDisk(String name);

	void persistMovie(String movieName) throws TechnicalAccessException;

}
