package br.com.caelum.pm73.dao;

import org.hibernate.*;
import org.junit.*;

import br.com.caelum.pm73.dominio.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UsuarioDaoTest {
	@Test
	public void maneiraErradaDeveEncontrarPeloNomeEEmailMockado() {
		Session session = mock(Session.class);
		Query query = mock(Query.class);
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario usuario = new Usuario("Formig�o A Cara Eu da Silva", "estaminosflaw@ant.com.br");
		
		String sql = "from Usuario u where u.nome = :nome and u.email = :email";
		when(session.createQuery(sql)).thenReturn(query);
		when(query.uniqueResult()).thenReturn(usuario);
		when(query.setParameter("nome", "Formig�o A Cara Eu da Silva")).thenReturn(query);
		when(query.setParameter("email", "estaminosflaw@ant.com.br")).thenReturn(query);
		
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Formig�o A Cara Eu da Silva", "estaminosflaw@ant.com.br");
		
		assertEquals(usuario.getNome(), usuarioDoBanco.getNome());
		assertEquals(usuario.getEmail(), usuarioDoBanco.getEmail());
	}
	
	@Test
	public void deveEncontrarPeloNomeEEmailMockado() {
		Session session = new CriadorDeSessao().getSession();
		UsuarioDao usuarioDao = new UsuarioDao(session);
		
		Usuario novoUsuario = new Usuario("Formig�o A Cara Eu da Silva", "estaminosflaw@ant.com.br");
		usuarioDao.salvar(novoUsuario);
		
		// Execute CriaTabelas para criar toda a estrutura necess�rio para funcionar o teste
		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("Formig�o A Cara Eu da Silva", "estaminosflaw@ant.com.br");
		
		assertEquals("Formig�o A Cara Eu da Silva", usuarioDoBanco.getNome());
		assertEquals("estaminosflaw@ant.com.br", usuarioDoBanco.getEmail());
		
		session.close();
	}
}
