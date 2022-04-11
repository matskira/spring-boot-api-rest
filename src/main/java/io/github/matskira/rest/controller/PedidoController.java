package io.github.matskira.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.matskira.domain.entity.ItemPedido;
import io.github.matskira.domain.entity.Pedido;
import io.github.matskira.domain.enums.StatusPedido;
import io.github.matskira.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.matskira.rest.dto.InformacaoItemPedidoDTO;
import io.github.matskira.rest.dto.InformacoesPedidoDTO;
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
	public Integer salvarPedido( @RequestBody @Valid PedidoDTO dtoPedido) {
		Pedido pedido = pedidoService.salvarPedido(dtoPedido);
		return pedido.getId();
	}
	
	@GetMapping(value = "consulta/{id}")
	public InformacoesPedidoDTO getById( @PathVariable Integer id ) {
		
		return pedidoService.obterPedidoCompleto(id).map(
					p -> converterPedidoDTO(p)).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
	}
	
	@PatchMapping(value = "atualiza/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id,@RequestBody AtualizacaoStatusPedidoDTO dto) {
		String status = dto.getNovoStatus();
		pedidoService.atualizaStatusPedido(	id, StatusPedido.valueOf(status));
	}
	
	private InformacoesPedidoDTO converterPedidoDTO( Pedido pedido ) {
		return InformacoesPedidoDTO.builder().codigo(pedido.getId())
							.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							.cpf(pedido.getCliente().getCpf())
							.nomeCliente(pedido.getCliente().getNome())
							.totalPedido(pedido.getTotal())
							.status(pedido.getStatus().name())
							.listaItems(converterItemPedidoDTO(pedido.getItemPedidos()))
							.build();
	}
	
	private List<InformacaoItemPedidoDTO> converterItemPedidoDTO( List<ItemPedido> itemPedido){
		if (CollectionUtils.isEmpty(itemPedido)) {
			return Collections.emptyList();
		}else {
			return itemPedido.stream().map(
					item -> InformacaoItemPedidoDTO.builder()
							.descricaoProduto(item.getProduto().getDescricao())
							.quantidade(item.getQuantidade())
							.precoUnitario(item.getProduto().getPreco())
							.build()
				).collect(Collectors.toList());
		}
	}
}
