package com.xtt.mediatheque.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.xtt.mediatheque.MoviesScan;

@Configuration
@PropertySource(value = { "blacklist.properties" })
public class AppConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public MoviesScan moviesScan() {
		MoviesScan moviesScan = new MoviesScan();
		moviesScan.setBlacklist(environment.getProperty("movies.words"));
		return moviesScan;
	}

}
