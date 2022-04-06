package io.github.matskira.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.matskira.domain.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	List<Cliente> findByNomeContains(String nome);

	// Caso eu queria criar um método onde vai usar uma HQL própria
	// @Query(value = " select c from Cliente c where c.nome like :nome")
	// List<Cliente> encontrarPorNome(@Param("nome") String nome);

	// Caso eu queria criar um método onde vai usar SQL nativo
	// @Query(value = " select * from cliente c where c.nome like '%nome%' ",
	// nativeQuery = true)
	// List<Cliente> encontrarPorNome(@Param("nome") String nome);

	void deleteByNome(String nome);

	// Caso eu queria criar um método onde vai usar HQL própria
	// @Query(value = " delete from Cliente c where c.nome = :nome ")
	// @Modifying //Mostra que vai fazer uma transação na base;
	// void deletarPorNome(@Param("nome") String nome);

	@Query(value = " select c from Cliente c left join fetch c.pedidos where c.id = :id")
	Cliente findClienteFetchPedidos(@Param(value = "id")Integer id);

	boolean existsByNome(String nome);
}
