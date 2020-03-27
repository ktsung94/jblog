package com.douzone.jblog.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/douzone/jblog/config/app/properties/jdbc.properties")
public class DBConfig {

	@Autowired
	private Environment env;


	//	<!-- Connection Pool DataSource -->
	//	<bean id="dataSource"
	//		class="org.apache.commons.dbcp.BasicDataSource">
	//		<property name="driverClassName"
	//			value="org.mariadb.jdbc.Driver" />
	//		<property name="url"
	//			value="jdbc:mysql://192.168.1.109:3307/jblog" />
	//		<property name="username" value="jblog" />
	//		<property name="password" value="jblog" />
	//	</bean>
	@Bean
	public DataSource basicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("jdbc.DriverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.Url"));
		basicDataSource.setUsername(env.getProperty("jdbc.Username"));
		basicDataSource.setPassword(env.getProperty("jdbc.Password"));
		basicDataSource.setInitialSize(env.getProperty("jdbc.InitialSize", Integer.class));
		basicDataSource.setMaxActive(env.getProperty("jdbc.MaxActive", Integer.class));
		return basicDataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

}
