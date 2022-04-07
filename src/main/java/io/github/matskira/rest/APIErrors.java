package io.github.matskira.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Data;
import lombok.Getter;

public class APIErrors {

	@Getter
	private List<String> errors;
	
	public APIErrors(String mensagemErro){
		this.errors = Arrays.asList(mensagemErro);
	}
}
