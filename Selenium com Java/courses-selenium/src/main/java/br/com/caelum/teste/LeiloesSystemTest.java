package br.com.caelum.teste;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.teste.pageobjects.LeiloesPage;
import br.com.caelum.teste.pageobjects.NovoLeilaoPage;
import br.com.caelum.teste.pageobjects.UsuarioPage;

public class LeiloesSystemTest {
	private WebDriver driver = null;
	private LeiloesPage leiloes = null;
	
	@BeforeClass
	public static void valeParaTodosOsTestes() throws IOException {
		System.setProperty("webdriver.chrome.driver", Paths.get("src/main/resources/chromedriver.exe").toRealPath().toString());
		// System.setProperty("webdriver.gecko.driver", Paths.get("src/main/resources/geckodriver.exe").toRealPath().toString());
	}
	
	@Before
	public void antesComecarCadaTeste() {
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/apenas-teste/limpa");
		
		leiloes = new LeiloesPage(driver);		
		
		UsuarioPage usuarios = new UsuarioPage(driver);
		usuarios.visita();
		usuarios.novo().cadastra("Bola de Pelo", "peludo@carvalho.com.br");
		
		leiloes.visita();
	}
	
	@After
	public void aposEncerrarCadaTeste() {
		driver.close();
	}
	
	@Test
	public void deveCadastrarUmLeilao() {
		NovoLeilaoPage form = leiloes.novo();
		form.preenche("Pote de mel", 500.0, "Bola de Pelo", true);
		
		assertTrue(leiloes.existe("Pote de mel", 500.0, "Bola de Pelo", true));
	}
	
	@Test
	public void informaNomeEValorInicialObrigatorios() {
		NovoLeilaoPage form = leiloes.novo();
		form.preenche("", 0, "Bola de Pelo", true);
		
		assertTrue(form.validacaoNomeObrigatorio());
		assertTrue(form.validacaoValorInicialMaiorQueZero());
	}
}
