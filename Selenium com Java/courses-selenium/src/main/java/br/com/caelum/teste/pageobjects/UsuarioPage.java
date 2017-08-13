package br.com.caelum.teste.pageobjects;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.com.caelum.teste.config.URLDaAplicacao;

public class UsuarioPage {
	private WebDriver driver = null;
	
	public UsuarioPage(WebDriver driver) {
		this.driver = driver;
	}

	public void visita() {
		driver.get(URLDaAplicacao.getUrlBase() + "/usuarios");
	}
	
	public NovoUsuarioPage novo() {
		driver.findElement(By.linkText("Novo Usuário")).click();
		return new NovoUsuarioPage(driver);
	}
	
	public boolean existeNaListagem(String nome, String email) {
		return driver.getPageSource().contains(nome) &&
				driver.getPageSource().contains(email);
	}

	public void deletaUsuarioNaPosicao(int posicao) {
		driver.findElements(By.tagName("button")).get(posicao-1).click();
		// pega o alert que está aberto
		Alert alert = driver.switchTo().alert();
		// confirma
		alert.accept();
	}

	public boolean temUsuarioCadastrado() {
		return driver.findElements(By.tagName("button")).size() > 0? true : false;
	}

	public void apagarUsuariosCadastrados() {
		while (driver.findElements(By.tagName("button")).size() > 0) {
			driver.findElements(By.tagName("button")).stream().findFirst().ifPresent(btn -> {
				btn.click();
				driver.switchTo().alert().accept();
			});
		}
	}

	public AlteraUsuarioPage editaUsuarioNaPosicao(int posicao) {
		driver.findElements(By.linkText("editar")).get(posicao-1).click();
		return new AlteraUsuarioPage(driver);
	}
}