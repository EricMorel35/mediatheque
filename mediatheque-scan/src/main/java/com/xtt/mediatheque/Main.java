package com.xtt.mediatheque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static java.lang.System.exit;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private MoviesScan moviesScan;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		moviesScan.searchMovies(args[0]);
		exit(0);
	}

}
