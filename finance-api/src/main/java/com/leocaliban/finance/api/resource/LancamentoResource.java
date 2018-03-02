package com.leocaliban.finance.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.service.LancamentoService;

/**
 * Classe {@link LancamentoResource} que expõe todos os recursos relacionados ao {@link Lancamento} (Controlador)
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@RestController //identifica a classe como controlador REST
@RequestMapping("/lancamentos") //define o mapeamento da requisição
public class LancamentoResource {
	
	@Autowired
	private LancamentoService service;
	
	/**
	 * Busca todos os Lançamentos do banco de dados através do service
	 * @return lista de lancamentos
	 */
	@GetMapping //indica o mapeamento GET padrão para /lancamentos (raiz)
	public List<Lancamento> listar(){
		return service.listar();
	}
	
	/**
	 * Busca um Lançamento no banco de dados pelo código através do service
	 * @param codigo código do lançamento, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Lancamento como entidade de resposta
	 */
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo){
		Lancamento lancamento = service.buscarPorCodigo(codigo);
		return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
	}

}
