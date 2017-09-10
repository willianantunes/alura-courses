package br.com.casadocodigo.loja.conf;

import java.nio.file.Paths;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Para permitir uso de um schema específico para testes.
 * Isso evita conflito com a base usada no projeto principal.
 */
public class DataSourceConfigurationTest {
    @Bean
    @Profile("test")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        
		/**
		 * Poderia até usar em memória pois a lógica de negócio está totalmente na aplicação, porém 
		 * vou deixar persistindo em arquivo para outros fins. Caso queira usar em memória:
		 * dataSource.setUrl("jdbc:hsqldb:mem:casadocodigo");
		 */
		dataSource.setUrl("jdbc:hsqldb:file:" + Paths.get("./my-tmp-db-test/data").toAbsolutePath());
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUsername("sa");
		dataSource.setPassword("");
		
        return dataSource;
    }
}
