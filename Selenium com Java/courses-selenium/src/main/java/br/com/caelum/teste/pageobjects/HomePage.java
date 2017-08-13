package br.com.caelum.teste.pageobjects;

import org.openqa.selenium.WebDriver;

import br.com.caelum.teste.config.URLDaAplicacao;

public class HomePage {
	private WebDriver driver = null;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void visita() {
		driver.get(URLDaAplicacao.getUrlBase());
	}
	
	public UsuarioPage menuUsuario() {
		UsuarioPage usuarioPage = new UsuarioPage(driver);
		usuarioPage.visita();
		return usuarioPage;
	}
}
