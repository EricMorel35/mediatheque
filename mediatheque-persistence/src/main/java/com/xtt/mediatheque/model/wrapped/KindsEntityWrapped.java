package com.xtt.mediatheque.model.wrapped;

import com.xtt.mediatheque.model.KindItem;

public class KindsEntityWrapped implements KindItem {
	
	private String kind;
	
	public KindsEntityWrapped(String kind) {
		this.kind = kind;
	}

	@Override
	public String getName() {
		return kind;
	}

}
