package com.xtt.mediatheque;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
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
		ws.setUrlCover(environment.getProperty("urlCover"));
		ws.setUrlYoutube(environment.getProperty("urlYoutube"));
		return ws;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(getMessageConverters());
		// TMDB v4 Read Access Token, sent as a Bearer header on every
		// request instead of the older v3 api_key query parameter (see
		// tmdb.properties).
		String token = environment.getProperty("tmdb.api_key");
		restTemplate.getInterceptors().add((request, body, execution) -> {
			request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
			return execution.execute(request, body);
		});
		return restTemplate;
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(new MappingJackson2HttpMessageConverter());
		return converters;
	}

}
