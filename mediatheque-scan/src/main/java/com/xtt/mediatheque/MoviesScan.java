package com.xtt.mediatheque;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.xtt.mediatheque.model.MovieSearchItem;
import com.xtt.mediatheque.service.MovieService;

@Component
public class MoviesScan {

	private final List<String> movies = new ArrayList<>();
	private MovieService movieService;
	private WSMovieDAO wsMovieDAO;

	@Value("${movies.words}")
	private String blacklist;

	private List<String> blacklistList;

	@Autowired
	public MoviesScan(MovieService movieService, WSMovieDAO wsMovieDAO) {
		this.movieService = movieService;
		this.wsMovieDAO = wsMovieDAO;
	}

	/**
	 * Main entry point of logic implemented for movie scanner. First step : it's a
	 * technical task to convert a String who contains a blacklist of words to erase
	 * from movies name. Second step : scan the directory from <code>path</code>
	 * Third step : when the scan is finished, persist movies found.
	 * 
	 * @param path : base path
	 */
	public void searchMovies(final String path) {
		blacklistList = convertStringIntoList(blacklist);
		scanDirectory(path);
		persistMovies();
	}

	/**
	 * Scan directory recursively from <code>path</code>. If a directory is found,
	 * <code>scanDirectory</code> method is called from the last directory found. If
	 * a file is found, a search is made with the goal to find forbidden words and
	 * if this case happens, forbidden words are deleted from movie name.
	 * 
	 * @param path : base path
	 */
	private void scanDirectory(final String path) {
		File root = new File(path);
		List<File> filesList = Arrays.asList(root.listFiles());

		if (!CollectionUtils.isEmpty(filesList)) {
			for (File file : filesList) {
				String fileName = StringUtils.EMPTY;
				if (StringUtils.isBlank(fileName)) {
					fileName = file.getAbsoluteFile().getName();
				}

				if (file.isDirectory()) {
					this.scanDirectory(file.getAbsolutePath());
				} else if (!file.isHidden()) {
					for (String searchedString : blacklistList) {
						if (fileName.contains(searchedString)) {
							fileName = fileName.replace(searchedString, StringUtils.EMPTY);
						}
					}
					movies.add(fileName);
				}
			}
		}
	}

	private List<String> convertStringIntoList(final String blacklist) {
		return Arrays.asList(blacklist.split(";"));
	}

	/**
	 * Calls Service layer of mediatheque to persist movie name found by scanner.
	 */
	private void persistMovies() {
		movies.stream().forEach(movie -> {
			MovieSearchItem movieItem = wsMovieDAO.getSearchResultsMovie(movie);
			if (movieItem != null && movieItem.getResults() > 0 && (!StringUtils.isEmpty(movieItem.getMovieName())
					|| !StringUtils.isEmpty(movieItem.getOriginalTitle()))) {
				movieService.saveMovie(movieItem);
			}
		});
	}

}
