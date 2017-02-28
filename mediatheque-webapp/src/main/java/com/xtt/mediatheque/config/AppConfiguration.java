package com.xtt.mediatheque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.xtt.mediatheque.AppServiceConfiguration;
import com.xtt.mediatheque.dao.AppDAOConfiguration;

@Configuration
@ComponentScan("com.xtt.mediatheque")
@Import(value = { AppServiceConfiguration.class, AppDAOConfiguration.class })
public class AppConfiguration {

	@Bean
	public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setDefaultEncoding("UTF-8");
		source.setBasenames("messages");

		return source;
	}

}
