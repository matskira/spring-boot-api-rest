package io.github.matskira.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.matskira.domain.entity.Cliente;
import io.github.matskira.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	Set<Pedido> findByCliente(Cliente cliente);

	@Query("  select p from Pedido p left join fetch p.itemPedidos where p.id = :id")
	Optional<Pedido> findByIdFetchItemPedidos(@Param( value = "id") Integer id);
}
