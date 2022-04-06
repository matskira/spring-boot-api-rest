package io.github.matskira.domain.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.matskira.domain.entity.Cliente;
import io.github.matskira.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	Set<Pedido> findByCliente(Cliente cliente);
}
