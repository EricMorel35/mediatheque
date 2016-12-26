package com.xtt.mediatheque;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.xtt.mediatheque.config.AppConfiguration;

public class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(
				AppConfiguration.class);
		MoviesScan moviesScan = context.getBean(MoviesScan.class);
		String directory = "Y:\\Films";

		if (!StringUtils.EMPTY.equalsIgnoreCase(directory)) {
			moviesScan
					.searchMovies(directory, moviesScan
							.convertStringIntoList(moviesScan.getBlacklist()));
			// t.writeFile();
			moviesScan.persistMovies();
		}

		((ConfigurableApplicationContext) context).close();
	}

}
