package com.leocaliban.finance.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	 * Busca todas as categorias do banco de dados
	 * @return lista de categorias
	 */
	@GetMapping //indica o mapeamento GET padrão para /categorias (raiz)
	public List<Categoria> listar(){
		return repository.findAll();
		
	}

}
