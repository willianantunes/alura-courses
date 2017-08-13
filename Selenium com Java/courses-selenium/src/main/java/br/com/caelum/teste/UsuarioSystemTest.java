package br.com.caelum.teste;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.teste.pageobjects.HomePage;
import br.com.caelum.teste.pageobjects.NovoUsuarioPage;
import br.com.caelum.teste.pageobjects.UsuarioPage;

public class UsuarioSystemTest {
	private WebDriver driver = null;
	private UsuarioPage usuarios = null;
	private HomePage home  = null;
	
	@BeforeClass
	public static void valeParaTodosOsTestes() throws IOException {
		System.setProperty("webdriver.chrome.driver", Paths.get("src/main/resources/chromedriver.exe").toRealPath().toString());
		// System.setProperty("webdriver.gecko.driver", Paths.get("src/main/resources/geckodriver.exe").toRealPath().toString());
	}
	
	@Before
	public void antesComecarCadaTeste() {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/apenas-teste/limpa");
		
		usuarios = new UsuarioPage(driver);
		home = new HomePage(driver);
		
		
		usuarios.visita();
	}
	
	@After
	public void aposEncerrarCadaTeste() {
		driver.close();
	}
	
//	@Test
	public void deveAdicionarUmUsuario() throws IOException {		
		usuarios.novo().cadastra("A Cara Eu", "reginaldo@acaraeu.com.br");
		
		assertTrue(usuarios.existeNaListagem("A Cara Eu", "reginaldo@acaraeu.com.br"));
	}

//	@Test
	public void deveInformarQueNomeObrigatorio() {
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("", "reginaldo@acaraeu.com.br");
		
		assertTrue(form.validacaoNomeObrigatorio());
	}
	
//	@Test
	public void deveInformarAmbasMensagemNomeEEmail() {
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("", "");
		
		assertTrue(form.validacaoNomeObrigatorio());
		assertTrue(form.validacaoEmailObrigatorio());
	}
	
//	@Test
	public void confirmaExclusaoUsuarioRecemCadastrado() {
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("A Cara Eu", "reginaldo@acaraeu.com.br");
		
		usuarios.deletaUsuarioNaPosicao(1);
		assertFalse(usuarios.existeNaListagem("A Cara Eu", "reginaldo@acaraeu.com.br"));		
	}
	
	@Test
	public void deveAlterarUsuario() {
		if (usuarios.temUsuarioCadastrado())
			usuarios.apagarUsuariosCadastrados();
		
		NovoUsuarioPage form = usuarios.novo();
		form.cadastra("A Cara Eu", "reginaldo@acaraeu.com.br");
		
		usuarios.editaUsuarioNaPosicao(1).altera("A Cara Eu...", "reginaldo.picapau@acaraeu.com.br");
		
		assertTrue(usuarios.existeNaListagem("A Cara Eu...", "reginaldo.picapau@acaraeu.com.br"));
	}
	
//	@Test
	public void linkNovoUsuarioFunciona() {
		home.visita();
		assertTrue(home.menuUsuario().novo().temBotaoSalvar());
	}
}