package io.github.matskira.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.matskira.domain.entity.Usuario;
import io.github.matskira.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioServiceImpl userService;
	private final PasswordEncoder encoder;
	
	
	@PostMapping(value = "/cadastro")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senhaCript = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCript);
		return userService.salvar(usuario);
	}
}
