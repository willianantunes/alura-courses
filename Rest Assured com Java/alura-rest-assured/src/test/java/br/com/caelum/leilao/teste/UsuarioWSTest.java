package br.com.caelum.leilao.teste;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.modelo.Usuario;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;

public class UsuarioWSTest {
	private Usuario mauricio;
	private Usuario guilherme;

	@BeforeClass
	public static void setUpForAll() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
	}
	
	@Before
	public void setUp() {
		mauricio = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		guilherme = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
	}
	
	// @Test
	public void deveRetornarListaDeUsuarios() {
		XmlPath path = given()
				.header("Accept", "application/xml")
				.get("/usuarios").xmlPath();
		
		// Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
		// Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
		List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);		
		
		assertEquals(2, usuarios.size());
		assertEquals(mauricio, usuarios.get(0));
		assertEquals(guilherme, usuarios.get(1));
	}
	
	// @Test
	public void deveRetornarUsuarioPeloId() {
		JsonPath path = given()
			.header("Accept", "application/json")
			// .param("usuario.id", 1)
			.queryParam("usuario.id", 1)
			.get("/usuarios/show")
			.andReturn().jsonPath();
		
		// http://localhost:8080/usuarios/show?usuario.id=1&_format=json
		// {"usuario": {"id": 1,"nome": "Mauricio Aniche","email": "mauricio.aniche@caelum.com.br"}}
		Usuario usuario = path.getObject("usuario", Usuario.class);
		
		assertEquals(mauricio, usuario);
	}
	
	// @Test
	public void deveAdicionarUmUsuario() {
		Usuario emi = new Usuario("Emi Narigudo", "nariga@emi.com.br");
		
		XmlPath path = given()
			.header("Accept", "application/xml")
			.contentType(ContentType.XML)
			.body(emi)
		.expect()
			.statusCode(200)
		.when()
			.post("/usuarios")
		.andReturn()
			.xmlPath();
		
		Usuario resposta = path.getObject("usuario", Usuario.class);
		
		assertEquals("Emi Narigudo", resposta.getNome());
		assertEquals("nariga@emi.com.br", resposta.getEmail());
	}
	
	@Test
	public void deveAdicionarUmUsuarioEDeletar() {
		Usuario joao = new Usuario("Joao da Silva", "joao@dasilva.com");
		
		XmlPath retorno = given()
				.header("Accept", "application/xml")
				.contentType(ContentType.XML)
				.body(joao)
			.expect()
				.statusCode(200)
			.when()
				.post("/usuarios")
			.andReturn()
				.xmlPath();

		Usuario resposta = retorno.getObject("usuario", Usuario.class);
		assertEquals("Joao da Silva", resposta.getNome());
		assertEquals("joao@dasilva.com", resposta.getEmail());
		
		given()
			.header("Accept", "application/xml")
			.contentType(ContentType.XML)
			.body(resposta)
		.expect()
			.statusCode(200)
		.when()
			.delete("/usuarios/deleta")
		.andReturn()
			.asString();
	}
	
/*	@Test
	public void deveDeletarUmUsuario() {
		given()
			.accept(ContentType.XML)
			.queryParam("usuario.id", 123)
		.when()
			.delete("/usuarios/deleta")
		.then()
			.statusCode(200);
	}*/
}
