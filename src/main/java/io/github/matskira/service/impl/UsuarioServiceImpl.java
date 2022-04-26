package io.github.matskira.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.matskira.domain.entity.Usuario;
import io.github.matskira.domain.repository.UsuarioRepository;
import io.github.matskira.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

	// Injeção do password encoder
	@Autowired
	private PasswordEncoder encoder;

	// Injeção do repository
	@Autowired
	private UsuarioRepository userRep;

	
	public UserDetails autenticar(Usuario usuario) {
		UserDetails usuarioDetails = loadUserByUsername(usuario.getLogin());
		boolean senhaValid = encoder.matches(usuario.getSenha(), usuarioDetails.getPassword());
		if (senhaValid) {
			return usuarioDetails;
		}
		throw new SenhaInvalidaException();
	}
	
	/**
	 * Método responsável por carregar os usuários na base de dados
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = userRep.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN"} : new String[] {"USER"};
		
		return User.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();

	}
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		return userRep.save(usuario);
	}

}
