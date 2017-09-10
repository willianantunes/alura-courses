package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Repository
@Transactional
public class ProdutoDao {
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	public List<Produto> listar() {
		return manager.createQuery("SELECT p FROM Produto p", Produto.class).getResultList();
	}

	public Produto find(Integer id) {
		return manager.find(Produto.class, id);
	}
	
	public BigDecimal somaPrecosPorTipo(TipoPreco tipoPreco) {
		return manager.createQuery("SELECT SUM(preco.valor) FROM Produto p "
				+ "JOIN p.precos preco WHERE preco.tipo = :tipoPreco", BigDecimal.class)
			.setParameter("tipoPreco", tipoPreco)
			.getSingleResult();
	}
}