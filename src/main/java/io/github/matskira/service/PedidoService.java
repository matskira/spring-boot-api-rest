package io.github.matskira.service;

import java.util.Optional;

import io.github.matskira.domain.entity.Pedido;
import io.github.matskira.domain.enums.StatusPedido;
import io.github.matskira.rest.dto.PedidoDTO;

public interface PedidoService {

	Pedido salvarPedido( PedidoDTO pedidoDTO );
	Optional<Pedido> obterPedidoCompleto( Integer id );
	void atualizaStatusPedido( Integer id , StatusPedido status);
}
