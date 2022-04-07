package io.github.matskira.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.matskira.domain.entity.Cliente;
import io.github.matskira.domain.entity.ItemPedido;
import io.github.matskira.domain.entity.Pedido;
import io.github.matskira.domain.entity.Produto;
import io.github.matskira.domain.repository.ClienteRepository;
import io.github.matskira.domain.repository.ItemPedidoRepository;
import io.github.matskira.domain.repository.PedidoRepository;
import io.github.matskira.domain.repository.ProdutoRepository;
import io.github.matskira.exception.RegraNegocioException;
import io.github.matskira.rest.dto.ItemPedidoDTO;
import io.github.matskira.rest.dto.PedidoDTO;
import io.github.matskira.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private PedidoRepository repository;
	private ClienteRepository clienteRep;
	private ProdutoRepository produtoRep;
	private ItemPedidoRepository itemPedidoRep;

	public PedidoServiceImpl(PedidoRepository repository, ClienteRepository clienteRep, ProdutoRepository produtoRep, ItemPedidoRepository itemPedidoRep) {
		this.repository = repository;
		this.clienteRep = clienteRep;
		this.produtoRep = produtoRep;
		this.itemPedidoRep = itemPedidoRep;
	}

	@Override
	@Transactional
	public Pedido salvarPedido(PedidoDTO pedidoDTO){
		Integer idCliente = pedidoDTO.getIdCliente();
		Cliente cliente = clienteRep.findById(idCliente).orElseThrow(()-> new RegraNegocioException("Código de cliente inválido!"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(pedidoDTO.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		
		List<ItemPedido> itemsPedido = converterItens(pedido, pedidoDTO.getItens());
		repository.save(pedido);
		itemPedidoRep.saveAll(itemsPedido);
		pedido.setItemPedidos(itemsPedido);
		return pedido;
	}
	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itemPedidos){
		if (itemPedidos.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
		}
		
		return itemPedidos.stream().map(
					dto -> {
						Integer idProduto = dto.getCodProduto();
						Produto produto = produtoRep.findById(idProduto).orElseThrow(()-> new RegraNegocioException("Código do produto inválido: " +idProduto));
						
						ItemPedido itemPedido = new ItemPedido();
						itemPedido.setQuantidade(dto.getQuantidade());
						itemPedido.setPedido(pedido);
						itemPedido.setProduto(produto);
						return itemPedido;
					}
				).collect(Collectors.toList());
	}
	
}
