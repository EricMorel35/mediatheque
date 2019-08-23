package com.xtt.mediatheque.wrapped;

import com.xtt.mediatheque.model.KindItem;
import com.xtt.mediatheque.tmdb.model.Genre;

public class KindItemWrapped implements KindItem {

	private final Genre kind;

	public KindItemWrapped(final Genre kind) {
		this.kind = kind;
	}

	@Override
	public String getName() {
		return kind.getName();
	}

}
