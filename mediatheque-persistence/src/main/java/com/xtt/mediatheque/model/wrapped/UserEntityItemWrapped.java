package com.xtt.mediatheque.model.wrapped;

import com.xtt.mediatheque.model.UserEntity;
import com.xtt.mediatheque.model.entity.UserEntityItem;

public class UserEntityItemWrapped implements UserEntityItem {
	private final UserEntity userEntity;

	public UserEntityItemWrapped(final UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	@Override
	public String getId() {
		return String.valueOf(userEntity.getId());
	}

	@Override
	public String getName() {
		return userEntity.getNom();
	}

}
