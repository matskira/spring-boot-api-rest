package io.github.matskira.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.matskira.domain.entity.Pedido;
import io.github.matskira.rest.dto.PedidoDTO;
import io.github.matskira.service.PedidoService;

@RestController
@RequestMapping(value = "api/pedidos")
public class PedidoController {

	private PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@PostMapping(value = "cadastro")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Integer salvarPedido( @RequestBody PedidoDTO dtoPedido) {
		Pedido pedido = pedidoService.salvarPedido(dtoPedido);
		return pedido.getId();
	}
}
