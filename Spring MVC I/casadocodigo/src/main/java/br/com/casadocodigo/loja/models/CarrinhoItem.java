package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class CarrinhoItem implements Serializable {
	private static final long serialVersionUID = -8270788285762184816L;
	
	private Produto produto;
	private TipoPreco tipoPreco;

	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		this.produto = produto;
		this.tipoPreco = tipoPreco;
	}
	
	public BigDecimal getPreco() {
		return produto.precoPara(tipoPreco);
	}

	public BigDecimal getTotal(Integer quantidade) {
		return this.getPreco().multiply(new BigDecimal(quantidade));
	}
}