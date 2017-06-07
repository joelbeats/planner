package se.joeladonai.userapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EnableJpaRepositories("se.joeladonai.userapi.repository")
@EnableTransactionManagement
@Configuration
public class DataSourceConfig {
	public static final String databaseUrl = "jdbc:mysql://localhost:3306/userapi?useSSL=false";
	public static final String databaseUsername = "root";
	public static final String databasePassword = "";

	@Bean
	public DataSource dataSource() {

		HikariConfig config = new HikariConfig();
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl(databaseUrl);
		config.setUsername(databaseUsername);
		config.setPassword(databasePassword);

		return new HikariDataSource(config);
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setGenerateDdl(true);
		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource());
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setPackagesToScan("se.joeladonai.userapi.model");
		return factory;
	}
}
