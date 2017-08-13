package br.com.caelum.teste;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.caelum.teste.cenario.CriadorDeCenarios;

public class LanceSystemTest {
	private WebDriver driver = null;
	private LeiloesPage leiloes = null;
	
	@BeforeClass
	public static void valeParaTodosOsTestes() throws IOException {
		System.setProperty("webdriver.chrome.driver", Paths.get("src/main/resources/chromedriver.exe").toRealPath().toString());
	}
	
	@Before
	public void antesComecarCadaTeste() {
		driver = new ChromeDriver();		
		leiloes = new LeiloesPage(driver);
		
		// Cenário padrão
		CriadorDeCenarios criadorDeCenarios = new CriadorDeCenarios(driver);
		criadorDeCenarios.limpaBaseDeDados()
			.umUsuario("Bola de Pelo", "peludo@carvalho.com.br")
			.umUsuario("Emi Topete", "emi@topete.com.br")
			.umLeilao("Bola de Pelo", "Garoupa", 100, false);
	}
	
	@After
	public void aposEncerrarCadaTeste() {
		driver.close();
	}	
	
	@Test
	public void deveFazerUmLance() {
		DetalhesDoLeilaoPage lances = leiloes.detalhes(1);
		
		lances.lance("Emi Topete", 150);
		
		assertTrue(lances.existeLance("Emi Topete", 150));
	}
}