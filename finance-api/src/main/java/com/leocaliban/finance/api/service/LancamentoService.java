package com.leocaliban.finance.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.repository.LancamentoRepository;
import com.leocaliban.finance.api.repository.PessoaRepository;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
import com.leocaliban.finance.api.repository.projection.ResumoLancamento;
import com.leocaliban.finance.api.service.exceptions.PessoaInexistenteOuInativaException;

/**
 * Classe {@link LancamentoService} é responsável pelas regras de negócio que envolvem Lancamento
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@Service //Indica ao spring que essa classe pode ser injetada
public class LancamentoService {

	@Autowired 
	private PessoaRepository pessoaRepository;
	
	@Autowired 
	private LancamentoRepository lancamentoRepository;

	/**
	 * Método que recupera por filtragem todos os Lançamentos do banco de dados através do repository.
	 * @param filter filtro da listagem de lançamentos.
	 * @return páginas de lançamentos.
	 */
	public Page<Lancamento> listar(LancamentoFilter filter, Pageable pageable){
		return lancamentoRepository.filtrar(filter, pageable);
	}
	
	/**
	 * Busca os Lançamentos do banco de dados de forma resumida.
	 * @param filter LancamentoFilter que contém os atributos que serão filtrados
	 * @param pageable paginação da listagem de lançamentos
	 * @return Páginas de lançamentos resumidos.
	 */
	public Page<ResumoLancamento> resumirListagem(LancamentoFilter filter, Pageable pageable) {
		return lancamentoRepository.resumir(filter, pageable);
	}
	
	/**
	 * Método que busca um lançamento no banco de dados.
	 * @param codigo código identificador do lançamento.
	 * @return Lancamento 
	 */
	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = lancamentoRepository.findOne(codigo);
		return lancamento;
	}
	
	/**
	 * Salva um lançamento no banco de dados.
	 * @param lancamento Lançamento que será salvo.
	 * @return lancamento salvo.
	 */
	public Lancamento salvar(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		return lancamentoSalvo;
	}
	
	/**
	 * Remove um lançamento no bando de dados através do repository.
	 * @param codigo Código do lançamento que será excluído.
	 */
	public void remover(Long codigo) {
		lancamentoRepository.delete(codigo);
	}

}
