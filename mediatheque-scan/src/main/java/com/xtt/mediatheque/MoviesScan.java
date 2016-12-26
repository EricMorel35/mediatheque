package com.xtt.mediatheque;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MoviesScan {

	private String blacklist;
	private final List<String> movies = new ArrayList<String>();

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
							fileName = fileName.replace(searchedString,
									StringUtils.EMPTY);
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

	public void persistMovies() {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(
					"jdbc:postgresql://192.168.1.20:5432/mediatheque",
					"postgres", "bobiwan1");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (con != null) {
			for (String movieName : movies) {
				try {
					Statement s = con.createStatement();
					ResultSet rs = s
							.executeQuery("select max(id) from allocine_liste");
					int nextId = 0;
					while (rs.next()) {
						nextId = rs.getInt(1);
						nextId++;
					}
					PreparedStatement statement = con
							.prepareStatement("insert into allocine_liste (id,datecrea,nomclean,idallocine,typesupport,idutil,nomorigine) values(?,?,?,0,1,1,?)");
					statement.setInt(1, nextId);
					statement.setDate(2,
							new Date(new java.util.Date().getTime()));
					statement.setString(3, "");
					statement.setString(4, movieName);
					statement.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
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
