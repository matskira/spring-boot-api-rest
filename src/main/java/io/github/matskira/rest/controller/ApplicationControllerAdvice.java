package io.github.matskira.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
