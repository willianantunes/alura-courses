package br.com.casadocodigo.loja.validation;

import java.util.Optional;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

public class ProdutoValidation implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "paginas", "typeMismatch");

		Produto produto = (Produto) target;

		Optional.ofNullable(produto.getPaginas()).filter(i -> i <= 0).ifPresent(i -> {
			errors.rejectValue("paginas", "field.required");
		});			
	}
}