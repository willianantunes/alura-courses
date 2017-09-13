package br.com.alura.listavip.conf;

import java.nio.file.Paths;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JPAConfiguration {
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:hsqldb:file:" + Paths.get("./my-tmp-db/data").toAbsolutePath());
		dataSource.setDriverClassName(JDBCDriver.class.getName());
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
        return dataSource;
    }
}
