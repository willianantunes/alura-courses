package br.com.caelum.leilao.teste;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.modelo.Leilao;
import br.com.caelum.leilao.modelo.Usuario;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

public class LeilaoWSTest {

	private Leilao geladeira;

	@Before
	public void setUp() {
		// http://localhost:8080/leiloes/show?leilao.id=1&_format=json
		// {"leilao": {"id": 1,"nome": "Geladeira","valorInicial": 800.0,"usuario": {"id": 1,"nome": "Mauricio Aniche","email": "mauricio.aniche@caelum.com.br"},"usado": false}}
		geladeira = new Leilao(1L, "Geladeira", 800.0, new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br"), false);
	}
	
	@Test
	public void deveRetornarLeilaoPeloId() {
		JsonPath path = given()
				.header("Accept", "application/json")
				.queryParam("leilao.id", 1)
				.get("/leiloes/show")
				.andReturn().jsonPath();

		Leilao leilao = path.getObject("leilao", Leilao.class);

		assertEquals(geladeira, leilao);
	}
	
	@Test
	public void deveRetornarQuantidadeDeLeiloes() {
		XmlPath path = given()
				.header("Accept", "application/xml")
				.get("/leiloes/total")
				.andReturn().xmlPath();
		
		// http://localhost:8080/leiloes/total?_format=xml
		// <int>2</int>
		int myValue = path.getInt("int");
		
		assertEquals(2, myValue);
	}
	
	@Test
	public void deveAdicionarUmLeilaoEDeletar() {
		Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");
		Leilao leilao = new Leilao("Formigueiro da Zona Leste", 100.0, joao, true);
		
		XmlPath retorno = given()
				.header("Accept", "application/xml")
				.contentType(ContentType.XML)
				.body(leilao)
			.expect()
				.statusCode(200)
			.when()
				.post("/leiloes")
			.andReturn()
				.xmlPath();

		Leilao resposta = retorno.getObject("leilao", Leilao.class);
		assertEquals("Formigueiro da Zona Leste", resposta.getNome());
		assertEquals("Joao da Silva", resposta.getUsuario().getNome());
		assertEquals(100.0, resposta.getValorInicial(), 0.001);
		
		given()
			.header("Accept", "application/xml")
			.contentType(ContentType.XML)
			.body(resposta)
		.expect()
			.statusCode(200)
		.when()
			.delete("/leiloes/deletar")
		.andReturn()
			.asString();
	}
}
