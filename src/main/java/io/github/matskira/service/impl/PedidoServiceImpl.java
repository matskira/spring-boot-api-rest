package io.github.matskira.service.impl;

import org.springframework.stereotype.Service;

import io.github.matskira.domain.repository.PedidoRepository;
import io.github.matskira.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private PedidoRepository repository;

	public PedidoServiceImpl(PedidoRepository repository) {
		this.repository = repository;
	}
	
	
	
	
}
