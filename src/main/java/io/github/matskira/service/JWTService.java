package io.github.matskira.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.matskira.VendasApplication;
import io.github.matskira.domain.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	@Value("${security.jwt.expiracao}")
	private String expiracao;
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;

	public String geraToken(Usuario usuario) {
		Long expiracaoLong = Long.valueOf(this.expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoLong);
		Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);

		return Jwts.builder().setSubject(usuario.getLogin()).setExpiration(data)
				.signWith(SignatureAlgorithm.HS512, this.chaveAssinatura).compact();

	};
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
		JWTService service = contexto.getBean(JWTService.class);
		Usuario usuario = Usuario.builder().login("teste").build();
		String token = service.geraToken(usuario);
		System.out.println(token);
	}

}
