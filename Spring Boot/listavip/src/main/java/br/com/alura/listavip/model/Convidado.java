package br.com.alura.listavip.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Convidado {
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String email;
	private String telefone;
}
