package br.com.caelum.pm73.dao;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.*;

import br.com.caelum.pm73.dominio.*;

public class LeilaoDaoTest {
	private Session session;
	private UsuarioDao usuarioDao;
	private LeilaoDao leilaoDao;

	@Before
	public void antesDeCadaTeste() {
		session = new CriadorDeSessao().getSession();
		usuarioDao = new UsuarioDao(session);
		leilaoDao = new LeilaoDao(session);
		
		session.beginTransaction();
	}
	
	@After
	public void aposCadaTeste() {
		session.getTransaction().rollback();
		session.close();
	}
	
	@Test
	public void devoContarLeilaoEncerrados() {
		Usuario loureiro = new Usuario("VLoureiro", "loureiro@v.com.br");
		Usuario coelho = new Usuario("SCoelho", "rabbit@coelho.com.br");
		
		Leilao ativo = new Leilao("Geladeira", 1500.0, loureiro, false);
		Leilao encerrado = new Leilao("Livro", 700.0, loureiro, false);
		encerrado.encerra();
		
		usuarioDao.salvar(loureiro);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);
		
		long total = leilaoDao.total();
		
		assertEquals(1L, total);
	}
	
	@Test
	public void deveRetornarZeroSeNaoHaLeiloesNovos() {
		Usuario loureiro = new Usuario("VLoureiro", "loureiro@v.com.br");
		Usuario coelho = new Usuario("SCoelho", "rabbit@coelho.com.br");
		
		Leilao l1 = new Leilao("Geladeira", 1500.0, loureiro, false);
		l1.encerra();
		Leilao l2 = new Leilao("Livro", 700.0, coelho, false);
		l2.encerra();
		
		usuarioDao.salvar(loureiro);
		usuarioDao.salvar(coelho);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		
		long total = leilaoDao.total();
		
		assertEquals(0L, total);
	}
	
	@Test
	public void retornaUmNovoDadoDoisLeiloes() {
		Usuario loureiro = new Usuario("VLoureiro", "loureiro@v.com.br");
		Usuario coelho = new Usuario("SCoelho", "rabbit@coelho.com.br");
		
		Leilao l1 = new Leilao("Geladeira", 1500.0, loureiro, false);
		Leilao l2 = new Leilao("Livro", 700.0, coelho, true);
		
		usuarioDao.salvar(loureiro);
		usuarioDao.salvar(coelho);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		
		List<Leilao> novos = leilaoDao.novos();
		
		assertTrue(novos.contains(l1));
		assertTrue(novos.size() == 1);
	}
	
	@Test
	public void retornaApenasAnteriores7Dias() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -10);
		
		Usuario loureiro = new Usuario("VLoureiro", "loureiro@v.com.br");
		Usuario coelho = new Usuario("SCoelho", "rabbit@coelho.com.br");
		
		Leilao l1 = new Leilao("Geladeira", 1500.0, loureiro, false);
		l1.setDataAbertura(calendar);
		Leilao l2 = new Leilao("Livro", 700.0, coelho, true);
		l2.setDataAbertura(Calendar.getInstance());
		
		usuarioDao.salvar(loureiro);
		usuarioDao.salvar(coelho);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		
		List<Leilao> antigos = leilaoDao.antigos();
		
		assertTrue(antigos.contains(l1));
		assertTrue(antigos.size() == 1);
	}
	
	@Test
	public void validaLimite7Dias() {
		Calendar c1 = Calendar.getInstance();
		c1.add(Calendar.DAY_OF_MONTH, -7);
		
		Calendar c2 = Calendar.getInstance();
		c2.add(Calendar.DAY_OF_MONTH, -6);
		
		Calendar c3 = Calendar.getInstance();
		c3.add(Calendar.DAY_OF_MONTH, -8);
		
		Usuario loureiro = new Usuario("VLoureiro", "loureiro@v.com.br");
		Usuario coelho = new Usuario("SCoelho", "rabbit@coelho.com.br");
		
		Leilao l1 = new Leilao("Geladeira", 1500.0, loureiro, false);
		l1.setDataAbertura(c1);
		Leilao l2 = new Leilao("Livro", 700.0, coelho, true);
		l2.setDataAbertura(c2);
		Leilao l3 = new Leilao("Livro", 700.0, coelho, true);
		l3.setDataAbertura(c3);
		
		usuarioDao.salvar(loureiro);
		usuarioDao.salvar(coelho);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		leilaoDao.salvar(l3);
		
		List<Leilao> antigos = leilaoDao.antigos();
		
		assertTrue(antigos.contains(l1));
		assertTrue(antigos.contains(l3));
		assertTrue(antigos.size() == 2);
	}
}
