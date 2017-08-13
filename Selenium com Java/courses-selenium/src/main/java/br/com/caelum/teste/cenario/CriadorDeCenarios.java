package br.com.caelum.teste.cenario;

import org.openqa.selenium.WebDriver;

import br.com.caelum.teste.LeiloesPage;
import br.com.caelum.teste.UsuarioPage;

public class CriadorDeCenarios {

	private WebDriver driver;

	public CriadorDeCenarios(WebDriver driver) {
		this.driver = driver;
	}
	
	public CriadorDeCenarios limpaBaseDeDados() {
		driver.get("http://localhost:8080/apenas-teste/limpa");
		return this;
	}
	
	public CriadorDeCenarios umUsuario(String nome, String email) {
		UsuarioPage usuarios = new UsuarioPage(driver);
		usuarios.visita();
		usuarios.novo().cadastra(nome, email);

		return this;
	}

	public CriadorDeCenarios umLeilao(String usuario, String produto, double valor, boolean usado) {
		LeiloesPage leiloes = new LeiloesPage(driver);
		leiloes.visita();
		leiloes.novo().preenche(produto, valor, usuario, usado);

		return this;
	}
}