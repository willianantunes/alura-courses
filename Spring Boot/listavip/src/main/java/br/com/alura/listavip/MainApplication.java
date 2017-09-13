package br.com.alura.listavip;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;

/**
 * Documentação COMPLETAÇA do Spring Boot <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/">aqui</a>!
 */
@EnableCaching
@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(MainApplication.class)
			.bannerMode(Banner.Mode.OFF)
			// "logging.level.org.springframework.web=DEBUG"
			.properties("spring.jpa.hibernate.ddl-auto=update", "spring.jpa.show-sql=true")
			.run(args);
	}
}