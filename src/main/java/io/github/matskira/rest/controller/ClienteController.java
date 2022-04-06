package io.github.matskira.rest.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.matskira.domain.entity.Cliente;
import io.github.matskira.domain.repository.ClienteRepository;

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

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private ClienteRepository clienteRep;
	
	public ClienteController(ClienteRepository clienteRep) {
		this.clienteRep = clienteRep;
	}

	/** 
	 * Método responsável por retornar clientes por ID;
	 * @param Integer id
	 * @return Objeto Cliente
	 * */
	@GetMapping(value = "/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> getClienteById(@PathVariable(name = "id") Integer id ) {
		
		Optional<Cliente> cliente = clienteRep.findById(id);
		if (cliente.isPresent()) {
			//Exemplo de response entity com maiores informações
			//HttpHeaders headers = new HttpHeaders();
			//List<String> strings = new ArrayList<String>();
			//strings.add("token");
			//headers.put("Authorization", strings);
			//ResponseEntity<Cliente> response = new ResponseEntity<Cliente>(cliente.get(), headers, HttpStatus.OK);
			
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	/** 
	 * Método responsável por salvar Clientes
	 * @param Cliente cliente
	 * @return dados do cliente salvo na base
	 * */
	@PostMapping(value = "/cadastro")
	@ResponseBody
	public ResponseEntity<Cliente> saveCliente( @RequestBody Cliente cliente ){
		Cliente clienteSalvo = clienteRep.save(cliente);
		
		return ResponseEntity.ok(clienteSalvo);
	}
	
	/** 
	 * Método responsável por excluir clientes na base de dados por ID
	 * @param Integer id
	 * @return Retorna 204 sem conteúdo
	 * */
	@DeleteMapping(value = "/exclui/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> deleteCliente(@PathVariable Integer id ){
		Optional<Cliente> clienteSalvo = clienteRep.findById(id);
		if (clienteSalvo.isPresent()) {
			clienteRep.delete(clienteSalvo.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(value = "/atualiza/{id}")
	@ResponseBody
	public ResponseEntity atualizaCliente( @RequestBody Cliente cliente,  @PathVariable Integer id ){
		//exemplo de uso do map do Objeto Optional
		return clienteRep.findById(id).map(clienteExistente ->{
			cliente.setId(clienteExistente.getId());
			clienteRep.save(cliente);
			return ResponseEntity.noContent().build();
		}).orElseGet(()->ResponseEntity.notFound().build());	
	}
	
}
