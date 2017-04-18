package com.xtt.mediatheque.dao.movie;

import java.util.List;

import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.model.KindItem;

public interface KindDAO {

	List<KindItem> getKinds() throws TechnicalAccessException;

}
