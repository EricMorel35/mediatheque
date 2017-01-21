package com.xtt.mediatheque.wrapped;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.model.MoviesList;
import com.xtt.mediatheque.model.ProductionCountry;

public class MovieSearchWrapped implements MovieSearchItem {

	private final MoviesList moviesList;

	public MovieSearchWrapped(final MoviesList moviesList) {
		this.moviesList = moviesList;
	}

	@Override
	public String getMovieName() {
		return moviesList.getResults().get(0).getTitle();
	}

	@Override
	public String getOriginalTitle() {
		return moviesList.getResults().get(0).getTitle();
	}

	@Override
	public String getTitle() {
		return moviesList.getResults().get(0).getTitle();
	}

	@Override
	public String getReleaseYear() {
		if (moviesList.getResults().get(0) != null && moviesList.getResults().get(0).getRelease_date() != null) {
			Date date = moviesList.getResults().get(0).getRelease_date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy");
			return dateFormat.format(date);
		} else {
			return "-1";
		}
	}

	@Override
	public String getURLPoster() {
		return moviesList.getResults().get(0).getPoster_path();
	}

	@Override
	public float getUserRatings() {
		return moviesList.getResults().get(0).getVote_average();
	}

	@Override
	public String getIdBackend() {
		return String.valueOf(moviesList.getResults().get(0).getId());
	}

	@Override
	public int getResults() {
		if (moviesList != null) {
			return moviesList.getTotal_results();
		} else {
			return 0;
		}
	}

	@Override
	public List<String> getCountries() {
		List<String> movies = new ArrayList<String>();
		if (moviesList.getResults().get(0) != null
				&& moviesList.getResults().get(0).getProduction_countries() != null) {
			for (ProductionCountry country : moviesList.getResults().get(0).getProduction_countries()) {
				movies.add(country.getName());
			}
			return movies;
		} else {
			return null;
		}
	}

}
