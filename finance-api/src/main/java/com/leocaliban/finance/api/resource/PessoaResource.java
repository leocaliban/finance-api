package com.leocaliban.finance.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.repository.PessoaRepository;

/**
 * Classe que expõe todos os recursos relacionados a Pessoa (Controlador)
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
@RestController //identifica a classe como controlador REST
@RequestMapping("/pessoas") //define o mapeamento da requisição
public class PessoaResource {
	
	@Autowired
	private PessoaRepository repository;
	
	/**
	 * Busca todas as pessoas do banco de dados através do repository
	 * @return lista de pessoas
	 */
	@GetMapping //indica o mapeamento GET padrão para /pessoas (raiz)
	public List<Pessoa> listar(){
		return repository.findAll();
	}
	
	/**
	 * Salva pessoas no banco de dados através do repository
	 * @param pessoa recurso recuperado do corpo da requisição
	 * @param response variavel de resposta para o http
	 * @return retorna o conteudo do objeto em json com um created 201, 
	 * substituindo a anotação @ResponseStatus que só retorna o status
	 */
	@PostMapping //indica o mapeamento POST padrão para /pessoas
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaSalva = repository.save(pessoa);
		//Montando a URI da requisição atual
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(pessoaSalva.getCodigo()).toUri();
		//Criando o Header de retorno, indicando onde o recurso pode ser acessado (REST)
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
	
	/**
	 * Busca uma pessoa no banco de dados pelo código através do repository
	 * @param codigo código da pessoa, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Pessoa
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = repository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	
}
