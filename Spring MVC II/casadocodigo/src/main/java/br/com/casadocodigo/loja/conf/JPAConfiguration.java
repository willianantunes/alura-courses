package br.com.casadocodigo.loja.conf;

import java.nio.file.Paths;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.dialect.HSQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
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
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties aditionalProperties) {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		factoryBean.setDataSource(dataSource);
		factoryBean.setJpaProperties(aditionalProperties);
		factoryBean.setPackagesToScan(Produto.class.getPackage().getName());

		return factoryBean;
	}
	
    @Bean
    @Profile("dev")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:hsqldb:file:" + Paths.get("./my-tmp-db/data").toAbsolutePath());
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
        return dataSource;
    }
    
    @Bean
    @Profile("dev")
    public Properties aditionalProperties(){
        Properties props = new Properties();
		props.setProperty("hibernate.dialect", HSQLDialect.class.getName());
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
    }
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	    return new JpaTransactionManager(emf);
	}	
}