package io.github.matskira.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.matskira.domain.entity.Usuario;
import io.github.matskira.exception.SenhaInvalidaException;
import io.github.matskira.rest.dto.CredenciaisDTO;
import io.github.matskira.rest.dto.TokenDTO;
import io.github.matskira.security.jwt.JWTService;
import io.github.matskira.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioServiceImpl userService;
	private final PasswordEncoder encoder;
	private final JWTService jwtService;
	
	@PostMapping(value = "/cadastro")
	@ResponseStatus(value = HttpStatus.CREATED)
	public Usuario salvar(@RequestBody @Valid Usuario usuario) {
		String senhaCript = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCript);
		return userService.salvar(usuario);
	}

	@PostMapping(value = "/auth")
	public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais) {
		try {
			Usuario usuario = Usuario.builder().login(credenciais.getLogin()).senha(credenciais.getSenha()).build();
			UserDetails userDet = userService.autenticar(usuario);
			String token = jwtService.geraToken(usuario);
			return new TokenDTO(usuario.getLogin(), token);
		} catch (UsernameNotFoundException  | SenhaInvalidaException exc) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED ,exc.getMessage());
		}
	}
}
