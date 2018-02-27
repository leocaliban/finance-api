package com.leocaliban.finance.api.resource;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.finance.api.model.Categoria;
import com.leocaliban.finance.api.repository.CategoriaRepository;

/**
 * Classe que expõe todos os recursos relacionados a Categoria (Controlador)
 * @author Leocaliban
 *
 * 26 de fev de 2018
 */
@RestController //identifica a classe como controlador REST
@RequestMapping("/categorias") //define o mapeamento da requisição
public class CategoriaResource {

	@Autowired
	private CategoriaRepository repository;
	
	/**
	 * Busca todas as categorias do banco de dados através do repository
	 * @return lista de categorias
	 */
	@GetMapping //indica o mapeamento GET padrão para /categorias (raiz)
	public List<Categoria> listar(){
		return repository.findAll();
		
	}
	

	/**
	 * Salva categorias no banco de dados através do repository
	 * @param categoria recurso recuperado do corpo da requisição
	 * @param response variavel de resposta para o http
	 * @return retorna o conteudo do objeto em json com um created 201, substituindo a anotação @ResponseStatus que só retorna o status
	 */
	@PostMapping //indica o mapeamento POST padrão para /categorias
	//@ResponseStatus(HttpStatus.CREATED) ao terminar a execução do método será retornado como resposta um 201 created
	public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = repository.save(categoria);
		
		//Montando a URI da requisição atual
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		//Criando o Header de retorno, indicando onde o recurso pode ser acessado (REST)
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(categoriaSalva);
			
	}
	
	/**
	 * Busca uma categoria no banco de dados pelo código através do repository
	 * @param codigo código da categoria, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Categoria
	 */
	@GetMapping("/{codigo}") //indica o mapeamento GET para o caminho /categorias/{codigo} 
	public Categoria buscarPorCodigo(@PathVariable Long codigo) {
		return repository.findOne(codigo);
	}
}
