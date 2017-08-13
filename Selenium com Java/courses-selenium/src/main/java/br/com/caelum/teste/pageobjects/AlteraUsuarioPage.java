package br.com.caelum.teste.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AlteraUsuarioPage {
	private WebDriver driver = null;
	
	public AlteraUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public void altera(String nome, String email) {
		WebElement campoNome = driver.findElement(By.name("usuario.nome"));
		WebElement campoEmail = driver.findElement(By.name("usuario.email"));
		
		campoNome.clear(); 
		campoNome.sendKeys(nome);
		campoEmail.clear(); 
		campoEmail.sendKeys(email);
		
		campoNome.submit();	
	}
}
