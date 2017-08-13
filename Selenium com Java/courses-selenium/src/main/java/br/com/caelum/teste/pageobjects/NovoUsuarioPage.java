package br.com.caelum.teste.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NovoUsuarioPage {
	private WebDriver driver = null;
	
	public NovoUsuarioPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void cadastra(String nome, String email) {
		WebElement campoNome = driver.findElement(By.name("usuario.nome"));
		WebElement campoEmail = driver.findElement(By.name("usuario.email"));
		
		campoNome.sendKeys(nome);
		campoEmail.sendKeys(email);
		
		campoNome.submit();		
	}

	public boolean temBotaoSalvar() {
		return driver.getPageSource().contains("Salvar!");
	}

	public boolean validacaoNomeObrigatorio() {
		return driver.getPageSource().contains("Nome obrigatorio!");
	}

	public boolean validacaoEmailObrigatorio() {
		return driver.getPageSource().contains("E-mail obrigatorio!");
	}
}