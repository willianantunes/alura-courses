package br.com.casadocodigo.loja.conf;

import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppWebConfiguration.class, JPAConfiguration.class, 
				MyWebSecurityConfiguration.class, JPAProductionConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter(Charset.forName("UTF-8").toString());
		
		/**
		 * Para manter a sessão inclusive na view, basta incluir o filtro OpenEntityManagerInViewFilter 
		 * para o Spring auxiliar no processo evitando assim LazyInitializationException...
		 * return new Filter[] { encodingFilter, new OpenEntityManagerInViewFilter() };
		 */
		return new Filter[] { encodingFilter, new OpenEntityManagerInViewFilter() };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
	
	/**
	 * Necessário comentar pois agora o parâmetro é passado via plugin do Tomcat no Maven.
	 */
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//	    servletContext.addListener(new RequestContextListener());
//	    servletContext.setInitParameter("spring.profiles.active", "dev");
//	}
}