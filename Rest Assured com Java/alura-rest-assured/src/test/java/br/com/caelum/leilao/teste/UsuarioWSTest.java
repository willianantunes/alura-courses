package br.com.caelum.leilao.teste;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.com.caelum.leilao.modelo.Usuario;
import io.restassured.path.xml.XmlPath;

public class UsuarioWSTest {
	@Test
	public void deveRetornarListaDeUsuarios() {
		XmlPath path = given()
				.header("Accept", "application/xml")
				.get("/usuarios").xmlPath();
		
		// Usuario usuario1 = path.getObject("list.usuario[0]", Usuario.class);
		// Usuario usuario2 = path.getObject("list.usuario[1]", Usuario.class);
		List<Usuario> usuarios = path.getList("list.usuario", Usuario.class);
		
		Usuario esperado1 = new Usuario(1L, "Mauricio Aniche", "mauricio.aniche@caelum.com.br");
		Usuario esperado2 = new Usuario(2L, "Guilherme Silveira", "guilherme.silveira@caelum.com.br");
		
		assertEquals(usuarios.size(), 2);
		assertEquals(usuarios.get(0), esperado1);
		assertEquals(usuarios.get(1), esperado2);
	}
}
