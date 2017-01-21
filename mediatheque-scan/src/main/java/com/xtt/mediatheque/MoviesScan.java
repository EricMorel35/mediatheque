package com.xtt.mediatheque;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xtt.mediatheque.dao.PersistenceDAO;
import com.xtt.mediatheque.exceptions.TechnicalAccessException;
import com.xtt.mediatheque.manager.MovieManager;

public class MoviesScan {

	private String blacklist;
	private final List<String> movies = new ArrayList<String>();

	@Autowired
	private MovieManager movieManager;

	@Autowired
	private PersistenceDAO persistenceDAO;

	@Autowired
	private WSMovieDAO wsMovieDAO;

	public void searchMovies(final String path, final List<String> listBlackList) {
		File root = new File(path);
		List<File> filesList = Arrays.asList(root.listFiles());

		if (filesList != null) {
			for (File file : filesList) {
				String fileName = StringUtils.EMPTY;
				if (StringUtils.isBlank(fileName)) {
					fileName = file.getAbsoluteFile().getName();
				}

				if (file.isDirectory()) {
					this.searchMovies(file.getAbsolutePath(), listBlackList);
					System.out.println("Dir:" + file.getAbsoluteFile());
				} else if (!file.isHidden()) {
					for (String searchedString : listBlackList) {
						if (fileName.contains(searchedString)) {
							fileName = fileName.replace(searchedString, StringUtils.EMPTY);
						}
					}
					System.out.println("File:" + fileName);
					movies.add(fileName);
				}
			}
		}

	}

	public List<String> convertStringIntoList(final String blacklist) {
		return Arrays.asList(blacklist.split(";"));
	}

	public void writeFile() {
		try {
			File file = new File("movies.txt");

			if (!file.exists()) {
				file.delete();
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (String content : movies) {
				bw.write(content);
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void persistMovies() throws TechnicalAccessException {
		for (String movieName : movies) {
			movieManager.saveMovie(movieName);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Log
			}
		}
	}

	public void setBlacklist(final String blacklist) {
		this.blacklist = blacklist;
	}

	public String getBlacklist() {
		return blacklist;
	}
}
