package com.leocaliban.finance.api.resource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.leocaliban.finance.api.dto.LancamentoEstatisticaCategoriaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaDiariaDTO;
import com.leocaliban.finance.api.dto.LancamentoEstatisticaPessoaDTO;
import com.leocaliban.finance.api.event.RecursoCriadoEvent;
import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
import com.leocaliban.finance.api.repository.projection.ResumoLancamento;
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
	 * @param pageable paginação da listagem de lançamentos
	 * @return paginas de lancamentos
	 */
	@GetMapping //indica o mapeamento GET padrão para /lancamentos (raiz)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> listar(LancamentoFilter filter, Pageable pageable){
		return service.listar(filter, pageable);
	}
	
	/**
	 * Busca os Lançamentos do banco de dados de forma resumida.
	 * @param filter LancamentoFilter que contém os atributos que serão filtrados
	 * @param pageable paginação da listagem de lançamentos
	 * @return Páginas de lançamentos resumidos.
	 */
	@GetMapping(params = "resumo") 
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable){
		return service.resumirListagem(filter, pageable);
	}
	
	/**
	 * Busca um Lançamento no banco de dados pelo código através do service
	 * @param codigo código do lançamento, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @GetMapping
	 * @return Lancamento como entidade de resposta
	 */
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<Lancamento> buscarPorCodigo(@PathVariable Long codigo){
		try {
			Lancamento lancamento = service.buscarPorCodigo(codigo);
			return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
		}
		catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/estatistica/por-categoria")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoEstatisticaCategoriaDTO> buscarPorCategoria(){
		return this.service.buscarPorCategoria();
	}
	
	@GetMapping("/estatistica/por-dia")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoEstatisticaDiariaDTO> buscarPorDia(){
		return this.service.buscarPorDia();
	}
	
	@GetMapping("/estatistica/por-pessoa")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public List<LancamentoEstatisticaPessoaDTO> buscarPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim){
		return this.service.buscarPorPessoa(inicio, fim);
	}
	
	
	@GetMapping("/relatorios/por-pessoa")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<byte[]> relatorioPorPessoa(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inicio, 
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fim) throws Exception{
		
		byte[] relatorio = service.relatorioPorPessoa(inicio, fim);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
				.body(relatorio);
		
	}
	
	/**
	 * Salva lancamentos no banco de dados através do service.
	 * @param lancamento recurso recuperado do corpo da requisição
	 * @param response variavel de resposta para o http
	 * @return retorna o conteudo do objeto em json com um created 201
	 */
	@PostMapping //indica o mapeamento POST padrão para /lancamentos
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> salvar (@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoSalvo = service.salvar(lancamento);
		
		//publica o evento ao ser acionado, 'this' referência a classe que está disparando o evento
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	
	/**
	 * Edita um lançamento do banco de dados
	 * @param codigo código da requisição
	 * @param lancamento recurso recuperado do corpo da requisição
	 * @return retorna o conteudo do objeto em json com um created 201
	 */
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> editar(@PathVariable Long codigo, @Valid @RequestBody Lancamento lancamento) {
		try {
			Lancamento lancamentoSalvo = service.editar(codigo, lancamento);
			return ResponseEntity.ok(lancamentoSalvo);
		} 
		catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * Deleta um lançamento do banco de dados pelo código através do service
	 * @param codigo código do lançamento, o valor será atribuido pelo @PathVariable que por sua vez recupera o valor do @DeleteMapping
	 */
	@DeleteMapping("/{codigo}") //indica o mapeamento DELETE para deletar o recurso no caminho /lancamentos/{codigo} 
	@ResponseStatus(HttpStatus.NO_CONTENT) //retorna o 204 porque não precisa de retorno ao excluir
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void remover(@PathVariable Long codigo) {
		service.remover(codigo);
	}
	
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	@PostMapping("/anexo")
	public String uploadAnexo(@RequestParam("anexo") MultipartFile anexo) throws IOException {
		OutputStream out = new FileOutputStream("C:\\Users\\leoca\\Documents\\STS\\anexo--" + anexo.getOriginalFilename());	
		out.write(anexo.getBytes());
		out.close();
		return "ok";
	}



}
