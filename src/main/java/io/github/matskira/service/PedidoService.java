package io.github.matskira.service;

import io.github.matskira.domain.entity.Pedido;
import io.github.matskira.rest.dto.PedidoDTO;

public interface PedidoService {

	Pedido salvarPedido( PedidoDTO pedidoDTO );
}
