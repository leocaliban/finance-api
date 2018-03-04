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
import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
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
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	/**
	 * Busca todos os Lançamentos do banco de dados através do service
	 * @return lista de lancamentos
	 */
	@GetMapping //indica o mapeamento GET padrão para /lancamentos (raiz)
	public List<Lancamento> listar(LancamentoFilter filter){
		return service.listar(filter);
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
	
	/**
	 * Salva lancamentos no banco de dados através do service.
	 * @param lancamento recurso recuperado do corpo da requisição
	 * @param response variavel de resposta para o http
	 * @return retorna o conteudo do objeto em json com um created 201
	 */
	@PostMapping //indica o mapeamento POST padrão para /lancamentos
	public ResponseEntity<Lancamento> salvar (@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = service.salvar(lancamento);
		
		//publica o evento ao ser acionado, 'this' referência a classe que está disparando o evento
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}

}
