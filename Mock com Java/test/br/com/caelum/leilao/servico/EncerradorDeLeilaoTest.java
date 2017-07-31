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
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import br.com.caelum.leilao.infra.dao.RepositorioDeLeiloes;
import br.com.caelum.leilao.infra.email.EnviadorDeEmail;

public class EncerradorDeLeilaoTest {
	
	RepositorioDeLeiloes daoFalso;
    EnviadorDeEmail enviadorFalso;
	
	@Before
	public void configuraAntesDeCadaTeste() {
		daoFalso = mock(RepositorioDeLeiloes.class);
	    enviadorFalso = mock(EnviadorDeEmail.class);
	}
	
    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        when(daoFalso.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();

        assertEquals(2, encerrador.getTotalEncerrados());
        assertTrue(leilao1.isEncerrado());
        assertTrue(leilao2.isEncerrado());
    }
    
    @Test
    public void naoEncerraLeiloesComecaramOntem() {
        Calendar antiga = Calendar.getInstance();
        Date ontem = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        antiga.setTime(ontem);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();
        List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

        when(daoFalso.correntes()).thenReturn(leiloesAntigos);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());
    }
 
    @Test
    public void seNadaEntaoEncerradorFazNada() {
    	LeilaoDao daoFalso = mock(LeilaoDao.class);
    	EnviadorDeEmail enviadorFalso = mock(EnviadorDeEmail.class);
    	when(daoFalso.correntes()).thenReturn(new ArrayList<Leilao>());
    	
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
    }
    
    @Test
    public void deveAtualizarLeizaoEncerrados() {
    	Calendar antiga = Calendar.getInstance();
    	antiga.setTime(Date.from(LocalDate.of(1999, 1, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    	
    	Leilao leilao1 = new CriadorDeLeilao().para("Nariz de Oliveira").naData(antiga).constroi();

    	when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
    	
    	EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
    	encerrador.encerra();
    	
    	// Verifica se o método atualiza foi chamado com leilao1 como parâmetro e se foi apenas 1 vez
    	verify(daoFalso, times(1)).atualiza(leilao1);
    }
    
    @Test
    public void naoDeveEncerrarLeiloesQueComecaramMenosDeUmaSemanaAtras() {
        Calendar ontem = Calendar.getInstance();
        ontem.add(Calendar.DAY_OF_MONTH, -1);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
            .naData(ontem).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
            .naData(ontem).constroi();

        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();

        assertEquals(0, encerrador.getTotalEncerrados());
        assertFalse(leilao1.isEncerrado());
        assertFalse(leilao2.isEncerrado());

        // verifys aqui
        verify(daoFalso, never()).atualiza(leilao1);
        verify(daoFalso, never()).atualiza(leilao2);
    }
    
    @Test
    public void leilaoEnviadoPorEmail() {
    	/**
    	 * Teste agora que o leilão é realmente enviado por e-mail mas garanta que os métodos foram executados 
    	 * nessa ordem específica: primeiro o DAO, depois o envio do e-mail.
    	 */
        Calendar antiga = Calendar.getInstance();
        antiga.setTime(Date.from(LocalDate.of(1999, 1, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        Leilao leilao1 = new CriadorDeLeilao().para("TV abudegada").naData(antiga).constroi();

        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();

        // Passamos os mocks que serao verificados
        InOrder inOrder = inOrder(daoFalso, enviadorFalso);
        // A primeira invocação
        inOrder.verify(daoFalso, times(1)).atualiza(leilao1);    
        // a segunda invocação
        inOrder.verify(enviadorFalso, times(1)).envia(leilao1);
    }
    
    @Test
    public void deveContinuarExecucaoMesmoQuandoDaoFalha() {
    	Calendar antiga = Calendar.getInstance();
        antiga.setTime(Date.from(LocalDate.of(1999, 1, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        Leilao leilao1 = new CriadorDeLeilao().para("TV abudegada").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira engaroupada").naData(antiga).constroi();
        
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(daoFalso).atualiza(leilao1);
        
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();
        
        verify(daoFalso).atualiza(leilao2);
        verify(enviadorFalso).envia(leilao2);
        verify(enviadorFalso, never()).envia(leilao1);
    }
    
    @Test
    public void deveContinuarExecucaoMesmoQuandoEnviadorFalha() {
    	Calendar antiga = Calendar.getInstance();
        antiga.setTime(Date.from(LocalDate.of(1999, 1, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        Leilao leilao1 = new CriadorDeLeilao().para("TV abudegada").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira engaroupada").naData(antiga).constroi();
        
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(enviadorFalso).envia(leilao1);
        
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();
        
        verify(daoFalso).atualiza(leilao2);
        verify(enviadorFalso).envia(leilao2);
    }
    
    @Test
    public void deveContinuarExecucaoMesmoQuandoDaoFalhaCompletamente() {
    	Calendar antiga = Calendar.getInstance();
        antiga.setTime(Date.from(LocalDate.of(1999, 1, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        
        Leilao leilao1 = new CriadorDeLeilao().para("TV abudegada").naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira engaroupada").naData(antiga).constroi();
        
        when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));
        doThrow(new RuntimeException()).when(daoFalso).atualiza(Matchers.any(Leilao.class));
        
        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso, enviadorFalso);
        encerrador.encerra();
        
        verify(enviadorFalso, never()).envia(Matchers.any(Leilao.class));
    }
}