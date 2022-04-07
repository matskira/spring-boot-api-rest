package io.github.matskira.rest.controller;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.matskira.domain.entity.Produto;
import io.github.matskira.domain.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "api/produto")
public class ProdutoController {

	private ProdutoRepository produtoRep;

	public ProdutoController(ProdutoRepository produtoRep) {
		this.produtoRep = produtoRep;
	}
	
	/**
	 * Método responsável por consultar produtos por ID
	 * @param Integer id
	 * @return O produto
	 * @throws Retorna 404 se não for encontrado
	 *  */
	@GetMapping(value = "/{id}")
	public Produto consultarProdutoPorId(@PathVariable(name = "id") Integer id ) {
		return produtoRep.findById(id).orElseThrow(
				()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não foi encontrado com sucesso")
			);
	}
	
	/** 
	 * Método responsável por salvar Produtos
	 * @param Produto produto
	 * @return dados do produto salvo na base
	 * */
	@PostMapping(value = "/cadastro")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Produto saveProduto( @RequestBody Produto produto ){
		return produtoRep.save(produto);
	}
	
	/** 
	 * Método responsável por excluir produtos na base de dados por ID
	 * @param Integer id
	 * @return Retorna 204 sem conteúdo
	 * @throws Caso não encontre o objeto será retornado 404
	 * */
	@DeleteMapping(value = "/exclui/{id}")
	@ResponseStatus( code = HttpStatus.NO_CONTENT)
	public void deleteProduto(@PathVariable Integer id ){
		produtoRep.findById(id)
        .map( produto -> {
            produtoRep.delete(produto );
            return produto;
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Produto não encontrado") );
	}
	
	/** 
	 * Método responsável por atualizar produtos na base de dados 
	 * @param Produto produto
	 * @param Integer id
	 * @return Retorna 204 sem conteúdo
	 * */	
	@PutMapping(value = "/atualiza/{id}")
	@ResponseStatus( code = HttpStatus.NO_CONTENT)
	public void atualizaProduto( @RequestBody Produto produto,  @PathVariable Integer id ){
		//exemplo de uso do map do Objeto Optional
		produtoRep.findById(id).map(produtoExistente ->{
			produto.setId(produtoExistente.getId());
			produtoRep.save(produto);
			return produtoExistente;
		}).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Produto não encontrado"));	
	}
	
	/**
	 * Método responsável por consultar produto por vários tipos de pesquisa usando o Example
	 * @param Produto produto
	 * @return Retorna um ou vários dados de produto
	 * @see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example.usage
	 *  */
	@GetMapping(value = "/consulta_produto")
	public List<Produto> find( Produto filtro ){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		return produtoRep.findAll(example);
	}
}
