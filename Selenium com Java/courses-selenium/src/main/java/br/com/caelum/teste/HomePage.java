package br.com.caelum.teste;

import org.openqa.selenium.WebDriver;

public class HomePage {
	private WebDriver driver = null;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void visita() {
		driver.get("http://localhost:8080/");
	}
	
	public UsuarioPage menuUsuario() {
		UsuarioPage usuarioPage = new UsuarioPage(driver);
		usuarioPage.visita();
		return usuarioPage;
	}
}
