package com.xtt.mediatheque.service;

import java.util.List;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;

public interface KindService {

	List<KindsDTO> getKinds() throws TechnicalAccessException;

}
