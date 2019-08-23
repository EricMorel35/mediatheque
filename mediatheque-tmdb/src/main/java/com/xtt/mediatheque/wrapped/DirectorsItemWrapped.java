package com.xtt.mediatheque.wrapped;

import com.xtt.mediatheque.model.DirectorsItem;
import com.xtt.mediatheque.tmdb.model.PersonCrew;

public class DirectorsItemWrapped implements DirectorsItem {

	private final PersonCrew personCrew;

	public DirectorsItemWrapped(final PersonCrew personCrew) {
		this.personCrew = personCrew;
	}

	@Override
	public String getName() {
		return personCrew.getName();
	}

}
