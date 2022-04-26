package io.github.matskira.exception;

public class SenhaInvalidaException extends RuntimeException {

	public SenhaInvalidaException() {
		super("Senha inválida");
	}
}
