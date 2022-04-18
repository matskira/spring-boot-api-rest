package io.github.matskira.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UserDetailsService{

	//Injeção do password encoder
	@Autowired
	private PasswordEncoder encoder;
	
	
	/** 
	 * Método responsável por carregar os usuários na base de dados
	 * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (!username.equals("Matheus")) {
			throw new UsernameNotFoundException("Usuário não encontrado na base!");
		}
		
		return User.builder()
				.username("Matheus")
				.password(encoder.encode("123"))
				.roles("ADMIN", "USER")
				.build();
	}

}
