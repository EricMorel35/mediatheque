package com.xtt.mediatheque.dto.factory;

import com.xtt.mediatheque.dto.KindsDTO;
import com.xtt.mediatheque.model.KindItem;

public interface KindDTOFactory {

	KindsDTO buildKindsDTO(KindItem movieEntity);

}
