package com.xtt.mediatheque;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

		System.out.println(System.getProperty("user.name"));
		try {
			System.out.println(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		}

		// String directory = StringUtils.EMPTY;

		// try {
		// BufferedReader bufferRead = new BufferedReader(
		// new InputStreamReader(System.in));
		// directory = bufferRead.readLine();
		//
		// System.out.println(directory);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		String directory = "Y:\\Déja Vu";

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
