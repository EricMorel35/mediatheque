package com.xtt.mediatheque;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.xtt.mediatheque.impl.WSMovieDAOImpl;

@Configuration
@PropertySource(value = { "classpath:tmdb.properties" })
public class AppTMDBConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public WSMovieDAOImpl wsMovieDAO() {
		WSMovieDAOImpl ws = new WSMovieDAOImpl();
		ws.setMovieUrl(environment.getProperty("tmdb.movie.query"));
		ws.setSearchUrl(environment.getProperty("tmdb.search.query"));
		ws.setApiKey(environment.getProperty("tmdb.api_key"));
		return ws;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(getMessageConverters());
		return restTemplate;
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new MappingJackson2HttpMessageConverter());
		return converters;
	}

}
