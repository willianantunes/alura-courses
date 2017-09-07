package br.com.casadocodigo.loja.conf;

import java.nio.charset.Charset;
import java.nio.file.Paths;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.daos.ProdutoDao;

@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class, ProdutoDao.class })
public class AppWebConfiguration {
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setDefaultEncoding(Charset.forName("UTF-8").toString());
		messageSource.setBasename("/WEB-INF/messages");
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
}