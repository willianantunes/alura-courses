package br.com.casadocodigo.loja.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
public class Produto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String titulo;
	private String descricao;
	private Integer paginas;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataLancamento;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Preco> precos;
}