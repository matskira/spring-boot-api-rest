package io.github.matskira.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.matskira.exception.PedidoException;
import io.github.matskira.exception.RegraNegocioException;
import io.github.matskira.rest.APIErrors;

/** 
 * Serve para adicionarmos tratamento do estilo ExceptionHandler
 * Os ExceptionHandler servem para capturar erros e retornar código de status e mensagens
 * */

@RestControllerAdvice
public class ApplicationControllerAdvice {

	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public APIErrors handleRegraNegocioException(RegraNegocioException exc) {
		String msgErro = exc.getMessage();
		return new APIErrors(msgErro);
	}
	
	@ExceptionHandler(PedidoException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public APIErrors handlePedidoNotFoundException(PedidoException pexc) {
		return new APIErrors(pexc.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public APIErrors handleMethodNotValidExcpetion(MethodArgumentNotValidException exc) {
		List<String> errors = exc.getBindingResult().getAllErrors().stream().map(e-> e.getDefaultMessage()).collect(Collectors.toList());
		return new APIErrors(errors);
	}
}
