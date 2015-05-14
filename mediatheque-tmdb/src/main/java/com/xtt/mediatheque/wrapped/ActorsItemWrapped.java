package com.xtt.mediatheque.wrapped;

import com.xtt.mediatheque.model.ActorsItem;
import com.xtt.mediatheque.model.PersonCast;

public class ActorsItemWrapped implements ActorsItem {

	private final PersonCast personCast;

	public ActorsItemWrapped(final PersonCast personCast) {
		this.personCast = personCast;
	}

	@Override
	public String getName() {
		return personCast.getName();
	}

}
