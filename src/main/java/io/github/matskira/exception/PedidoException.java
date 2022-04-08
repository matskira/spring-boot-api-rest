package io.github.matskira.exception;

public class PedidoException extends RuntimeException{

	public PedidoException() {
		super("Pedido não encontrado");
	}
}
