package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class Preco {
	private BigDecimal valor;
    private TipoPreco tipo;
}