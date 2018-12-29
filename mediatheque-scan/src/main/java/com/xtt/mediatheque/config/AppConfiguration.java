package com.xtt.mediatheque.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import com.xtt.mediatheque.AppServiceConfiguration;
import com.xtt.mediatheque.AppTMDBConfiguration;
import com.xtt.mediatheque.dao.AppDAOConfiguration;

@Configuration
@PropertySource(value = { "blacklist.properties" })
@Import({ AppServiceConfiguration.class, AppTMDBConfiguration.class, AppDAOConfiguration.class })
@ComponentScan("com.xtt.mediatheque")
public class AppConfiguration {

}
