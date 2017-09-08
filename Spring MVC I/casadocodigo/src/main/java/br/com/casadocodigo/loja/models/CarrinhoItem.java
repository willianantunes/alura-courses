package br.com.casadocodigo.loja.models;

import lombok.Data;

@Data
public class CarrinhoItem {
	private Produto produto;
	private TipoPreco tipoPreco;

	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		this.produto = produto;
		this.tipoPreco = tipoPreco;
	}
}