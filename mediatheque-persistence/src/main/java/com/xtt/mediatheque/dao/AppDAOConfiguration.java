package com.xtt.mediatheque.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:persistence.properties" })
@ComponentScan("com.xtt.mediatheque")
public class AppDAOConfiguration {

}
