package com.xtt.mediatheque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xtt.mediatheque.manager.MovieManager;
import com.xtt.mediatheque.service.MovieService;
import com.xtt.mediatheque.service.impl.MovieServiceImpl;

@Configuration
public class AppServiceConfiguration {

	@Bean
	public MovieManager movieManager() {
		return new MovieManager();
	}

	@Bean
	public MovieService movieService() {
		return new MovieServiceImpl();
	}

	// @Bean
	// public MovieDTOFactory movieDTOFactory() {
	// return new MovieDTOFactoryImpl();
	// }

}
