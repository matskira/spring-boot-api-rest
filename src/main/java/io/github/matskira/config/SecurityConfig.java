package io.github.matskira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.github.matskira.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//Injeção do usuário service
	@Autowired
	private UsuarioServiceImpl serviceUser;
	
	
	/**
	 * Método responsável por criptografar a senha seguindo padrão BCrypt
	 * 
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Método responsável pelo algoritmo de autenticação
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/* Em memória
		 * auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("matheus")
				.password(passwordEncoder().encode("123")).roles("USER");
		 */
		
		// Usando base de dados
		auth.userDetailsService(serviceUser).passwordEncoder(passwordEncoder());
	}

	/**
	 * Método responsável pelo algoritmo de autorizar pós autenticação
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST,"/api/usuario/**").permitAll()
				.antMatchers("/api/clientes/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/api/produto/**").hasAnyRole("ADMIN")
				.antMatchers("/api/pedidos/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated()
				.and().httpBasic();
	}
}
