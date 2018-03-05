package com.leocaliban.finance.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.finance.api.event.RecursoCriadoEvent;
import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.repository.PessoaRepository;
import com.leocaliban.finance.api.service.PessoaService;

/**
 * Classe {@link PessoaResource} que expõe todos os recursos relacionados a Pessoa (Controlador)
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
@RestController //identifica a classe como controlador REST
@RequestMapping("/pessoas") //define o mapeamento da requisição
public class PessoaResource {
	
	@Autowired
	private PessoaRepository repository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private PessoaService service;
	
	/**
	 * Busca todas as pessoas do banco de dados através do repository
	 * @return lista de pessoas
	 */
	@GetMapping //indica o mapeamento GET padrão para /pessoas (raiz)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public List<Pessoa> listar(){
		return repository.findAll();
	}
	
	/**
	 * Salva pessoas no banco de dados através do repository.
	 * @param pessoa recurso recuperado do corpo da requisição
	 * @param response variavel de resposta para o http
	 * @return retorna o conteudo do objeto em json com um created 201, 
	 * substituindo a anotação @ResponseStatus que só retorna o status
	 */
	@PostMapping //indica o mapeamento POST padrão para /pessoas
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaSalva = repository.save(pessoa);

		//publica o evento ao ser acionado, this referencia a classe que está disparando o evento
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	
	/**
	 * Busca uma pessoa no banco de dados pelo código através do repository
	 * @param codigo código da pessoa, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Pessoa
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<Pessoa> buscarPorCodigo(@PathVariable Long codigo) {
		Pessoa pessoa = repository.findOne(codigo);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	/**
	 * Deleta uma pessoa do banco de dados pelo código através do repository
	 * @param codigo código da pessoa, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @DeleteMapping
	 */
	@DeleteMapping("/{codigo}") //indica o mapeamento DELETE para deletar o recurso no caminho /pessoas/{codigo} 
	@ResponseStatus(HttpStatus.NO_CONTENT) //retorna o 204 porque não precisa de retorno ao excluir
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		repository.delete(codigo);
	}
	
	/**
	 * Edita uma pessoa do banco de dados pelo código através do service
	 * @param codigo código da pessoa, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @PutMapping
	 * @param pessoa entidade Pessoa recuperada da requisição
	 * @return Pessoa que foi editada
	 */
	@PutMapping("/{codigo}") //indica o mapeamento PUT para atualizar o recurso no caminho /pessoas/{codigo} 
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> editar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = service.editar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}	
	
	/**
	 * Método que faz uma edição parcial do atributo Ativo da Pessoa
	 * @param codigo código da pessoa, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @PutMapping
	 * @param ativo atributo que foi editado
	 */
	@PutMapping("/{codigo}/ativo") //indica o mapeamento PUT para atualizar o recurso nesse caminho
	@ResponseStatus(HttpStatus.NO_CONTENT) //retorna o 204 porque não precisa de retorno ao editar
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		service.editarPropriedadeAtivo(codigo, ativo);
	}
}
