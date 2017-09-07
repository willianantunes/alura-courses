package br.com.casadocodigo.loja.conf;

import java.nio.file.Paths;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.casadocodigo.loja.models.Produto;

@EnableTransactionManagement
public class JPAConfiguration {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		// dataSource.setUrl("jdbc:hsqldb:mem:casadocodigo");
		dataSource.setUrl("jdbc:hsqldb:file:" + Paths.get("./my-tmp-db/data").toAbsolutePath());
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");

		factoryBean.setDataSource(dataSource);

		Properties props = new Properties();
		props.setProperty("hibernate.dialect", HSQLDialect.class.getName());
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");

		factoryBean.setJpaProperties(props);

		factoryBean.setPackagesToScan(Produto.class.getPackage().getName());

		return factoryBean;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	    return new JpaTransactionManager(emf);
	}	
}