package io.github.matskira.rest.controller;

import java.util.List;

import javax.validation.Valid;

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

import io.github.matskira.domain.entity.Cliente;
import io.github.matskira.domain.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// ANOTAÇÕES DE ESTUDO
/* O @RequestMapping pode receber varios parâmetros para configurar nossa rota de consumo
 * podemos passar o value, que representa o caminho, além disso, podemos colocar uma lista de caminhos
 * para o mesmo método.
 * Colocamos o method, para informar qual é o verbo HTTP responsável por aquela requisição.
 * Colocamos o consumes, para caso fosse um POST, podermos enviar no coropo algum arquivo json, xml e etc
 * Colocamos o produces, para informar que o método irá devolver um json, xml e etc
 * Ex:
 * @RequestMapping(value = {"api/clientes/hello/{nome}", "api/hello"},
 * 				   method = RequestMethod.POST,
 * 				   consumes = {"application/json", "application/xml"},
 * 				   produces = {"application/json", "application/xml"}
 * )
 * 
*/

@RestController
@RequestMapping("/api/clientes")
@Api("API Clientes")
public class ClienteController {

	private ClienteRepository clienteRep;

	public ClienteController(ClienteRepository clienteRep) {
		this.clienteRep = clienteRep;
	}

	/**
	 * Método responsável por retornar clientes por ID;
	 * 
	 * @param Integer id
	 * @return Objeto Cliente
	 */
	@GetMapping(value = "/{id}")
	@ApiOperation("Obter detalhes de um cliente")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cliente encontrado"),
			@ApiResponse(code = 404, message = "Cliente não encotrado para o ID informado") })
	public Cliente getClienteById(@PathVariable(name = "id") @ApiParam(name = "id do cliente") Integer id) {

		return clienteRep.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não foi encontrado com sucesso"));

	}

	/**
	 * Método responsável por salvar Clientes
	 * 
	 * @param Cliente cliente
	 * @return dados do cliente salvo na base
	 */
	@PostMapping(value = "/cadastro")
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation("Salvar dados de um cliente")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cliente Cadastrado"),
			@ApiResponse(code = 400, message = "Erro de validação ao tentar salvar o cliente") })
	public Cliente saveCliente(@RequestBody @Valid Cliente cliente) {
		return clienteRep.save(cliente);
	}

	/**
	 * Método responsável por excluir clientes na base de dados por ID
	 * 
	 * @param Integer id
	 * @return Retorna 204 sem conteúdo
	 */
	@DeleteMapping(value = "/exclui/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCliente(@PathVariable Integer id) {
		clienteRep.findById(id).map(cliente -> {
			clienteRep.delete(cliente);
			return cliente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	/**
	 * Método responsável por atualizar clientes na base de dados
	 * 
	 * @param Cliente cliente
	 * @param Integer id
	 * @return Retorna 204 sem conteúdo
	 */
	@PutMapping(value = "/atualiza/{id}")
	public void atualizaCliente(@RequestBody @Valid Cliente cliente, @PathVariable Integer id) {
		// exemplo de uso do map do Objeto Optional
		clienteRep.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			clienteRep.save(cliente);
			return clienteExistente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	/**
	 * Método responsável por consultar cliente por vários tipos de pesquisa usando
	 * o Example
	 * 
	 * @param Cliente cliente
	 * @return Retorna um ou vários dados de cliente
	 * @see https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#query-by-example.usage
	 */
	@GetMapping(value = "/consulta_cliente")
	public List<Cliente> find(Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		return clienteRep.findAll(example);
	}
}
