package br.com.caelum.pm73.dao;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.junit.*;

import br.com.caelum.pm73.dominio.*;
import br.com.caelum.pm73.dominio.builder.LeilaoBuilder;

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
		
		Leilao ativo = new LeilaoBuilder()
				.comDono(loureiro)
				.comNome("Geladeira")
				.comValor(1500.0)
				.constroi();

		Leilao encerrado = new LeilaoBuilder()
				.comDono(loureiro)
				.comNome("Livro")
				.comValor(700.0)
				.encerrado()
				.constroi();
		
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
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(loureiro)
				.comNome("Geladeira")
				.comValor(1500.0)
				.encerrado()
				.constroi();

		Leilao l2 = new LeilaoBuilder()
				.comDono(coelho)
				.comNome("Livro")
				.comValor(700.0)
				.encerrado()
				.constroi(); 
		
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
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(loureiro)
				.comNome("Geladeira")
				.comValor(1500.0)
				.constroi();  

		Leilao l2 = new LeilaoBuilder()
				.comDono(coelho)
				.comNome("Livro")
				.comValor(700.0)
				.usado()
				.constroi();  

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
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(loureiro)
				.comNome("Geladeira")
				.comValor(1500.0)
				.diasAtras(7)
				.constroi();

		Leilao l2 = new LeilaoBuilder()
				.comDono(coelho)
				.comNome("Livro")
				.comValor(700.0)
				.diasAtras(6)
				.usado()
				.constroi(); 

		Leilao l3 = new LeilaoBuilder()
				.comDono(coelho)
				.comNome("Livro")
				.comValor(700.0)
				.diasAtras(8)
				.usado()
				.constroi(); 
		
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
	
	@Test
	public void deveTrazerLeiloesNaoEncerradosNoPeriodo() {
		Calendar comecoDoIntervalo = Calendar.getInstance();
		comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
		Calendar fimDoIntervalo = Calendar.getInstance();
		
		Usuario moreira = new Usuario("Moreira", "moreira@madureira.com.br");
		Leilao l1 = new LeilaoBuilder()
				.comDono(moreira)
				.comNome("N64")
				.comValor(700.0)
				.diasAtras(2)
				.constroi();
		
		Leilao l2 = new LeilaoBuilder()
				.comDono(moreira)
				.comNome("Playstation")
				.comValor(1700.0)
				.diasAtras(20)
				.constroi();
		
		usuarioDao.salvar(moreira);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		
		
		List<Leilao> porPeriodo = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);
		assertTrue(porPeriodo.contains(l1));
		assertEquals(1, porPeriodo.size());
	}
	
	public void naoDeveTrazerLeiloesEncerradoNoPeriodo() {
		Calendar comecoDoIntervalo = Calendar.getInstance();
		comecoDoIntervalo.add(Calendar.DAY_OF_MONTH, -10);
		Calendar fimDoIntervalo = Calendar.getInstance();
		
		Usuario moreira = new Usuario("Moreira", "moreira@madureira.com.br");	
		
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(moreira)
				.comNome("N64")
				.comValor(700.0)
				.diasAtras(2)
				.encerrado()
				.constroi();
		
		usuarioDao.salvar(moreira);
		leilaoDao.salvar(l1);
		
		List<Leilao> porPeriodo = leilaoDao.porPeriodo(comecoDoIntervalo, fimDoIntervalo);
		
		assertEquals(0, porPeriodo.size());
	}
	
	@Test
	public void trazDoisDeTresMedianteIntervaloValor() {
		Usuario moreira = new Usuario("Moreira", "moreira@madureira.com.br");
		Usuario monica = new Usuario("Monica", "muzykant@wagner.com.br");
		Usuario gregorio = new Usuario("Jardel", "gregorio@wagner.com.br");
		Usuario antunes = new Usuario("Antunes", "antunes@baldness.com.br");
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("N64")
				.comValor(700.0)
				.diasAtras(2)
				.constroi();
		
		Leilao l2 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("Playstation")
				.comValor(1200.0)
				.constroi();	
		
		Leilao l3 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("Placa Radeon")
				.comValor(250.0)
				.constroi();
		
		l1.adicionaLance(new Lance(Calendar.getInstance(), moreira, 701.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), monica, 702.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 710.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), moreira, 800.0, l1));
		
		l2.adicionaLance(new Lance(Calendar.getInstance(), moreira, 5000.0, l2));
		l2.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 5001.0, l2));
		l2.adicionaLance(new Lance(Calendar.getInstance(), monica, 5002.0, l2));
		
		l3.adicionaLance(new Lance(Calendar.getInstance(), moreira, 251.0, l3));
		l3.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 252.0, l3));
		l3.adicionaLance(new Lance(Calendar.getInstance(), monica, 260.0, l3));
		
		usuarioDao.salvar(antunes);
		usuarioDao.salvar(moreira);
		usuarioDao.salvar(monica);
		usuarioDao.salvar(gregorio);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		leilaoDao.salvar(l3);
		
		List<Leilao> disputadosEntre = leilaoDao.disputadosEntre(600.0, 2100.0);
		assertTrue(disputadosEntre.contains(l1));
		assertTrue(disputadosEntre.contains(l2));
		assertEquals(2, disputadosEntre.size());
	}
	
	@Test
	public void devolveListaDeLeiloesSemRepeticoes() {
		Usuario moreira = new Usuario("Moreira", "moreira@madureira.com.br");
		Usuario monica = new Usuario("Monica", "muzykant@wagner.com.br");
		Usuario gregorio = new Usuario("Jardel", "gregorio@wagner.com.br");
		Usuario antunes = new Usuario("Antunes", "antunes@baldness.com.br");
		
		Leilao l1 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("N64")
				.comValor(700.0)
				.diasAtras(2)
				.constroi();
		
		Leilao l2 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("Playstation")
				.comValor(1200.0)
				.constroi();	
		
		Leilao l3 = new LeilaoBuilder()
				.comDono(antunes)
				.comNome("Placa Radeon")
				.comValor(250.0)
				.constroi();
		
		l1.adicionaLance(new Lance(Calendar.getInstance(), moreira, 701.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), monica, 702.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 710.0, l1));
		l1.adicionaLance(new Lance(Calendar.getInstance(), moreira, 800.0, l1));
		
		l2.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 5001.0, l2));
		l2.adicionaLance(new Lance(Calendar.getInstance(), monica, 5002.0, l2));
		
		l3.adicionaLance(new Lance(Calendar.getInstance(), moreira, 251.0, l3));
		l3.adicionaLance(new Lance(Calendar.getInstance(), gregorio, 252.0, l3));
		l3.adicionaLance(new Lance(Calendar.getInstance(), monica, 260.0, l3));
		
		usuarioDao.salvar(antunes);
		usuarioDao.salvar(moreira);
		usuarioDao.salvar(monica);
		usuarioDao.salvar(gregorio);
		leilaoDao.salvar(l1);
		leilaoDao.salvar(l2);
		leilaoDao.salvar(l3);
		
		List<Leilao> leiloesDoMoreira = leilaoDao.listaLeiloesDoUsuario(moreira);

		assertEquals(2, leiloesDoMoreira.size());
	}
	
    @Test
    public void listaSomenteOsLeiloesDoUsuario() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Usuario comprador2 = new Usuario("Guilherme", "g@g.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador2, 200.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador2, 100.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        usuarioDao.salvar(comprador2);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }	
    
    @Test
    public void listaDeLeiloesDeUmUsuarioNaoTemRepeticao() throws Exception {
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);

        List<Leilao> leiloes = leilaoDao.listaLeiloesDoUsuario(comprador);
        assertEquals(1, leiloes.size());
        assertEquals(leilao, leiloes.get(0));
    }  
    
    @Test
    public void valorInicialMedioDoUsuariParticipando3LeiloesDe3() {
		Usuario comprador = new Usuario("Moreira", "moreira@madureira.com.br");
		Usuario dono = new Usuario("Antunes", "antunes@baldness.com.br");
		
		Double vIni1 = 90d;
		Double vIni2 = 8d;
		Double vIni3 = 400d;
		Double ave = ((vIni1 + vIni2 + vIni3) / 3d);
		
        Leilao l1 = new LeilaoBuilder().comDono(dono).comValor(vIni1)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .comLance(Calendar.getInstance(), comprador, 200.0)
                .constroi();
        
        Leilao l2 = new LeilaoBuilder().comDono(dono).comValor(vIni2)
                .comLance(Calendar.getInstance(), comprador, 10.0)
                .comLance(Calendar.getInstance(), comprador, 25.0)
                .constroi();      
        
        Leilao l3 = new LeilaoBuilder().comDono(dono).comValor(vIni3)
                .comLance(Calendar.getInstance(), comprador, 401.0)
                .constroi();

        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(l1);
        leilaoDao.salvar(l2);
        leilaoDao.salvar(l3);
        
        Double valorInicialMedioDoUsuario = this.leilaoDao.getValorInicialMedioDoUsuario(comprador);
        
        assertEquals(ave, valorInicialMedioDoUsuario, 0.001);        
    }
    
    @Test
    public void valorInicialMedioDoUsuariParticipando2LeiloesDe3() {
		Usuario comprador = new Usuario("Moreira", "moreira@madureira.com.br");
		Usuario dono = new Usuario("Antunes", "antunes@baldness.com.br");
		
		Double vIni1 = 90d;
		Double vIni2 = 8d;
		Double vIni3 = 400d;
		Double ave = ((vIni1 + vIni2) / 2d);
		
        Leilao l1 = new LeilaoBuilder().comDono(dono).comValor(vIni1)
                .comLance(Calendar.getInstance(), comprador, 100.0)
                .comLance(Calendar.getInstance(), comprador, 200.0)
                .constroi();
        
        Leilao l2 = new LeilaoBuilder().comDono(dono).comValor(vIni2)
                .comLance(Calendar.getInstance(), comprador, 10.0)
                .constroi();      
        
        Leilao l3 = new LeilaoBuilder().comDono(dono).comValor(vIni3)
                .constroi();

        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(l1);
        leilaoDao.salvar(l2);
        leilaoDao.salvar(l3);
        
        Double valorInicialMedioDoUsuario = this.leilaoDao.getValorInicialMedioDoUsuario(comprador);
        
        assertEquals(ave, valorInicialMedioDoUsuario, 0.001);        
    }   
    
    @Test
    public void devolveAMediaDoValorInicialDosLeiloesQueOUsuarioParticipou(){
        Usuario dono = new Usuario("Mauricio", "m@a.com");
        Usuario comprador = new Usuario("Victor", "v@v.com");
        Leilao leilao = new LeilaoBuilder()
            .comDono(dono)
            .comValor(50.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .comLance(Calendar.getInstance(), comprador, 200.0)
            .constroi();
        Leilao leilao2 = new LeilaoBuilder()
            .comDono(dono)
            .comValor(250.0)
            .comLance(Calendar.getInstance(), comprador, 100.0)
            .constroi();
        usuarioDao.salvar(dono);
        usuarioDao.salvar(comprador);
        leilaoDao.salvar(leilao);
        leilaoDao.salvar(leilao2);

        assertEquals(150.0, leilaoDao.getValorInicialMedioDoUsuario(comprador), 0.001);
    }
    
    @Test
    public void deveDeletarUmLeilao() {       
        Leilao leilao = new LeilaoBuilder()
            .comValor(50.0)
            .constroi();
        
        usuarioDao.salvar(leilao.getDono());
        leilaoDao.salvar(leilao);        
        
        session.flush();

        leilaoDao.deleta(leilao);
        
        Leilao leilaoDeletado = leilaoDao.porId(leilao.getId());
        
        assertNull(leilaoDeletado);        
    }
}