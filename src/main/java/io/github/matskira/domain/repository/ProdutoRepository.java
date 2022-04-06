package io.github.matskira.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.matskira.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

	
}
