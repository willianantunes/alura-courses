package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = { "id" })
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titulo;
	@Lob
	private String descricao;
	private Integer paginas;
	/**
	 * Para java.time.* o bean configurado não funcionou. Fica para um TSHOOT posterior.
	 */
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLancamento;
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Preco> precos = new ArrayList<>();
	
	private String sumarioPath;

	public BigDecimal precoPara(TipoPreco tipoPreco) {
		return precos.stream().filter(p -> p.getTipo().equals(tipoPreco))
				.findFirst().get().getValor();		
	}
}