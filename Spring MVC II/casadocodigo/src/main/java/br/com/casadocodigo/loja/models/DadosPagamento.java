package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DadosPagamento {
	private BigDecimal value;

	public DadosPagamento(BigDecimal value) {
		this.value = value;		
	}
}