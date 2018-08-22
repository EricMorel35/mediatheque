package com.xtt.mediatheque.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.xtt.mediatheque.dao.movie.MovieDAO;
import com.xtt.mediatheque.dao.movie.impl.MovieDAOImpl;

@Configuration
@PropertySource(value = { "classpath:persistence.properties" })
public class AppDAOConfiguration {

	@Autowired
	private Environment environment;

	/*@Bean
	public PersistenceDAO persistenceDAO() {
		PersistenceDAOImpl persistenceDAO = new PersistenceDAOImpl();
		persistenceDAO.setUrlCover(environment.getProperty("urlCover"));
		return persistenceDAO;
	}*/

	@Bean
	public MovieDAO movieDAO() {
		return new MovieDAOImpl();
	}

	/*@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setPackagesToScan(new String[] { "com.xtt.mediatheque.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		sessionFactory.setAnnotatedClasses(MovieUserEntity.class, MovieEntity.class, MovieDirectorsEntity.class,
				MovieCountryEntity.class, MovieActorsEntity.class, MovieKindsEntity.class, MovieUserEntity.class);
		sessionFactory.setDataSource(restDataSource());
		return sessionFactory;
	}*/

	/*@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}*/

	/*@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}*/

//	@Bean
//	public DataSource restDataSource() {
//		EmbeddedDatabase db = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
//		return db;
//	}

	/*private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.put("hibernate.show_sql", "false");
		properties.put("hibernate.format_sql", "false");
		properties.put("hibernate.hbm2ddl.auto", "read");
		properties.put("hibernate.connection.autocommit", "true");
		return properties;
	}*/


	

}
