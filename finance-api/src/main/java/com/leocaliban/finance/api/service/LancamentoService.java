package com.leocaliban.finance.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.repository.LancamentoRepository;
import com.leocaliban.finance.api.repository.PessoaRepository;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
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
	 * @return uma lista de lançamentos.
	 */
	public List<Lancamento> listar(LancamentoFilter filter){
		return lancamentoRepository.filtrar(filter);
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
}
