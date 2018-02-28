package com.leocaliban.finance.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.finance.api.event.RecursoCriadoEvent;
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
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
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
	 * @return retorna o conteudo do objeto em json com um created 201, 
	 * substituindo a anotação @ResponseStatus que só retorna o status
	 */
	@PostMapping //indica o mapeamento POST padrão para /categorias
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = repository.save(categoria);
		
		//publica o evento ao ser acionado, this referencia a classe que está disparando o evento
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
			
	}

	/**
	 * Busca uma categoria no banco de dados pelo código através do repository
	 * @param codigo código da categoria, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Categoria
	 */
	@GetMapping("/{codigo}") //indica o mapeamento GET para o caminho /categorias/{codigo} 
	public ResponseEntity<Categoria> buscarPorCodigo(@PathVariable Long codigo) {
		Categoria categoriaRecuperada = repository.findOne(codigo);
		
		return categoriaRecuperada != null ? ResponseEntity.ok(categoriaRecuperada) : ResponseEntity.notFound().build();
	}
}
