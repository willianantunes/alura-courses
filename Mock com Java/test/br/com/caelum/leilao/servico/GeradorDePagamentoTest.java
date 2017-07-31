package br.com.caelum.leilao.servico;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Pagamento;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.dao.RepositorioDePagamentos;
import br.com.caelum.leilao.infra.email.EnviadorDeEmail;
import br.com.caelum.leilao.infra.relogio.Relogio;

public class GeradorDePagamentoTest {
	private RepositorioDeLeiloes leiloes;
	private RepositorioDePagamentos pagamentos;
	private Avaliador avaliador;
	private Relogio relogio;

	@Before
	public void configuraAntesDeCadaTeste() {
		leiloes = mock(RepositorioDeLeiloes.class);
		pagamentos = mock(RepositorioDePagamentos.class);
		avaliador = new Avaliador();
		relogio = mock(Relogio.class);
	}
	
	
	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrador() {
		Leilao leilao = new CriadorDeLeilao().para("Ketchup Farias")
				.lance(new Usuario("A Cara Eu"), 2000.0)
				.lance(new Usuario("Formigão"), 2500)
				.constroi();
		
		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		
		GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador);
		gerador.gera();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		/**
		 *  Além de verificar que o método foi invocado, ele também 
		 *  irá capturar o objeto já que não temos acesso direto a ele.
		 */
		verify(pagamentos).salva(argumento.capture());
		Pagamento pagamentoGerado = argumento.getValue(); // getValue retorna o object recebido pelo Mock
		
		assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);
	}

	@Test
	public void seSabadodeveEmpurrarProximoDiaUtil() {
		Leilao leilao = new CriadorDeLeilao().para("Ketchup Farias")
				.lance(new Usuario("A Cara Eu"), 2000.0)
				.lance(new Usuario("Formigão"), 2500)
				.constroi();
		
		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		
		Calendar sabado = Calendar.getInstance();
		sabado.set(2012, Calendar.APRIL, 7);
		
		when(relogio.hoje()).thenReturn(sabado);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador, relogio);
		gerador.gera();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());
		Pagamento pagamentoGerado = argumento.getValue();
		
		assertEquals(Calendar.MONDAY, pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
		assertEquals(9, pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));
	}
	
	@Test
	public void seDomingodeveEmpurrarProximoDiaUtil() {
		Leilao leilao = new CriadorDeLeilao().para("Ketchup Farias")
				.lance(new Usuario("A Cara Eu"), 2000.0)
				.lance(new Usuario("Formigão"), 2500)
				.constroi();
		
		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		
		Calendar domingo = Calendar.getInstance();
		domingo.set(2012, Calendar.APRIL, 8);
		
		when(relogio.hoje()).thenReturn(domingo);
		
		GeradorDePagamento gerador = new GeradorDePagamento(leiloes, pagamentos, avaliador, relogio);
		gerador.gera();
		
		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());
		Pagamento pagamentoGerado = argumento.getValue();
		
		assertEquals(Calendar.MONDAY, pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
		assertEquals(9, pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));
	}
}