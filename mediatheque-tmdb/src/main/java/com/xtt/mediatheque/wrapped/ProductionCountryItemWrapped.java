package com.xtt.mediatheque.wrapped;

import com.xtt.mediatheque.model.ProductionCountryItem;
import com.xtt.mediatheque.tmdb.model.ProductionCountry;

public class ProductionCountryItemWrapped implements ProductionCountryItem {

	private final ProductionCountry country;

	public ProductionCountryItemWrapped(final ProductionCountry country) {
		this.country = country;
	}

	@Override
	public String getCountryCode() {
		return country.getIso_3166_1();
	}

	@Override
	public String getName() {
		return country.getName();
	}

}
