package br.com.caelum.teste;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UsuarioPage {
	private WebDriver driver = null;
	
	public UsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get("http://localhost:8080/usuarios");
	}
	
	public NovoUsuarioPage novo() {
		driver.findElement(By.linkText("Novo Usuário")).click();
		return new NovoUsuarioPage(driver);
	}
	
	public boolean existeNaListagem(String nome, String email) {
		return driver.getPageSource().contains(nome) &&
				driver.getPageSource().contains(email);
	}
}