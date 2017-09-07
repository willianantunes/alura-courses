package br.com.casadocodigo.loja.exception;

import java.io.IOException;

public class PastaFileSaverNaoPodeSerCriadaException extends RuntimeException {
	public PastaFileSaverNaoPodeSerCriadaException(IOException e) {
		super(e);
	}
}