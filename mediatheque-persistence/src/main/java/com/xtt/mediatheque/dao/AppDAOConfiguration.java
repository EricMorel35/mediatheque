package com.xtt.mediatheque.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.xtt.mediatheque.dao.impl.PersistenceDAOImpl;
import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.impl.MovieDAOImpl;
import com.xtt.mediatheque.model.MovieActorsEntity;
import com.xtt.mediatheque.model.MovieCountryEntity;
import com.xtt.mediatheque.model.MovieDirectorsEntity;
import com.xtt.mediatheque.model.MovieEntity;
import com.xtt.mediatheque.model.MovieKindsEntity;
import com.xtt.mediatheque.model.MovieUserEntity;
import com.xtt.mediatheque.model.SupportEntity;
import com.xtt.mediatheque.model.UserEntity;

@Configuration
@PropertySource(value = { "classpath:persistence.properties" })
@EnableTransactionManagement
public class AppDAOConfiguration {

	@Autowired
	private Environment environment;

	@Bean
	public PersistenceDAO persistenceDAO() {
		PersistenceDAOImpl persistenceDAO = new PersistenceDAOImpl();
		persistenceDAO.setUrlCover(environment.getProperty("urlCover"));
		return persistenceDAO;
	}

	@Bean
	public MovieDAO movieDAO() {
		return new MovieDAOImpl();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(new String[] { "com.xtt.mediatheque.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setAnnotatedClasses(MovieUserEntity.class, MovieEntity.class, MovieDirectorsEntity.class,
				MovieCountryEntity.class, MovieActorsEntity.class, MovieKindsEntity.class, MovieUserEntity.class,
				SupportEntity.class, UserEntity.class);
		sessionFactory.setDataSource(restDataSource());
		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Bean
	public DataSource restDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/mediatheque");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");

		return dataSource;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "false");
		properties.put("hibernate.hbm2ddl.auto", "read");
		properties.put("hibernate.connection.autocommit", "true");
		return properties;
	}

}
