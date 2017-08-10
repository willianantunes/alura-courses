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
	private UsuarioPage usuarios = null;
	private HomePage home  = null;
	
	@BeforeClass
	public static void valeParaTodosOsTestes() throws IOException {
		System.setProperty("webdriver.gecko.driver", Paths.get("src/main/resources/geckodriver.exe").toRealPath().toString());
	}
	
	@Before
	public void antesComecarCadaTeste() {
		myDriver = new FirefoxDriver();
		usuarios = new UsuarioPage(myDriver);
		home = new HomePage(myDriver);
		
		usuarios.visita();
	}
	
	@After
	public void aposEncerrarCadaTeste() {
		myDriver.close();
	}
	
	@Test
	public void deveAdicionarUmUsuario() throws IOException {		
		usuarios.novo().cadastra("A Cara Eu", "reginaldo@acaraeu.com.br");
		
		assertTrue(usuarios.existeNaListagem("A Cara Eu", "reginaldo@acaraeu.com.br"));
	}

	@Test
	public void deveInformarQueNomeObrigatorio() {
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("", "reginaldo@acaraeu.com.br");
		
		assertTrue(form.validacaoNomeObrigatorio());
	}
	
	@Test
	public void deveInformarAmbasMensagemNomeEEmail() {
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("", "");
		
		assertTrue(form.validacaoNomeObrigatorio());
		assertTrue(form.validacaoEmailObrigatorio());
	}
	
	@Test
	public void linkNovoUsuarioFunciona() {
		home.visita();
		assertTrue(home.menuUsuario().novo().temBotaoSalvar());
	}
}