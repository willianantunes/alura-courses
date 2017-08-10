package br.com.caelum.teste;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class UsuarioSystemTest {
	private WebDriver myDriver = null;
	private UsuarioPage usuarios;
	
	@BeforeClass
	public static void valeParaTodosOsTestes() throws IOException {
		System.setProperty("webdriver.gecko.driver", Paths.get("src/main/resources/geckodriver.exe").toRealPath().toString());
	}
	
	@Before
	public void antesComecarCadaTeste() {
		myDriver = new FirefoxDriver();
		usuarios = new UsuarioPage(myDriver);
	}
	
	@After
	public void aposEncerrarCadaTeste() {
		myDriver.close();
	}
	
	@Test
	public void deveAdicionarUmUsuario() throws IOException {		
		myDriver.get("http://localhost:8080/usuarios/new");
		
		usuarios.visita();
		usuarios.novo().cadastra("A Cara Eu", "reginaldo@acaraeu.com.br");
		
		assertTrue(usuarios.existeNaListagem("A Cara Eu", "reginaldo@acaraeu.com.br"));
		
//		WebElement myNome = myDriver.findElement(By.name("usuario.nome"));
//		WebElement myEmail = myDriver.findElement(By.name("usuario.email"));
//		
//		String nomeDoUsuario = "A Cara Eu";
//		String emailDoUsuario = "reginaldo@acaraeu.com.br";
//		
//		myNome.sendKeys(nomeDoUsuario );		
//		myEmail.sendKeys(emailDoUsuario );
//		
//		// 1a opção: Clicando no botão
//		myDriver.findElement(By.id("btnSalvar")).click();
//		
//		// 2a opção: Usando qualquer elemento que está dentro do formulário
//		// myNome.submit(); 
//		// myEmail.submit();
//		
//		// getPageSource -> Devolve o código fonte da página que está sendo exibida no browser
//		boolean achouNome = myDriver.getPageSource().contains(nomeDoUsuario);
//		boolean achouEmail = myDriver.getPageSource().contains(emailDoUsuario);
//		
//		assertTrue(achouNome);
//		assertTrue(achouEmail);
	}
/*	
	@Test
	public void deveInformarQueNomeObrigatorio() {
		myDriver.get("http://localhost:8080/usuarios/new");
		
		WebElement myEmail = myDriver.findElement(By.name("usuario.email"));
		String emailDoUsuario = "reginaldo@acaraeu.com.br";
		myEmail.sendKeys(emailDoUsuario);
		myEmail.submit();
		
		boolean avisoNomeObrigatorio = myDriver.getPageSource().contains("Nome obrigatorio!");
		
		assertTrue(avisoNomeObrigatorio);
	}
	
	@Test
	public void deveInformarAmbasMensagemNomeEEmail() {
		myDriver.get("http://localhost:8080/usuarios/new");
		
		WebElement btnSalvar = myDriver.findElement(By.id("btnSalvar"));
		btnSalvar.click();
		
		boolean achouNomeObrigatorio = myDriver.getPageSource().contains("Nome obrigatorio!");
		boolean achouEmailObrigatorio = myDriver.getPageSource().contains("E-mail obrigatorio!");
		
		assertTrue(achouNomeObrigatorio);
		assertTrue(achouEmailObrigatorio);
	}
	
	@Test
	public void linkNovoUsuarioFunciona() {
		myDriver.get("http://localhost:8080/");
		
		WebElement opcaoMenu = myDriver.findElement(By.xpath("//*[@id=\"topmenu\"]/ul/li[2]/a"));
		opcaoMenu.click();
		
		myDriver.findElement(By.linkText("Novo Usuário")).click();
		
		boolean temBtnSalvar = myDriver.getPageSource().contains("Salvar!");
		
		assertTrue(temBtnSalvar);
	}*/
}
