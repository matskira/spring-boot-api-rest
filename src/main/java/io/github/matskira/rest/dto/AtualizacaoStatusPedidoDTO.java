package io.github.matskira.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizacaoStatusPedidoDTO {

	@NotEmpty(message = "Campo status não pode ser vazio")
	private String novoStatus;

}
