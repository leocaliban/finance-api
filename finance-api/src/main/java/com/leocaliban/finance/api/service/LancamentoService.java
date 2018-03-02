package com.leocaliban.finance.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.LancamentoRepository;

/**
 * Classe {@link LancamentoService} é responsável pelas regras de negócio que envolvem Lancamento
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@Service //Indica ao spring que essa classe pode ser injetada
public class LancamentoService {

	@Autowired 
	private LancamentoRepository repository;
	
	/**
	 * Método que recupera todos os Lançamentos do banco de dados através do repository
	 * @return uma lista de lançamentos
	 */
	public List<Lancamento> listar(){
		return repository.findAll();
	}
	
	/**
	 * Método que busca um lançamento no banco de dados
	 * @param codigo código identificador do lançamento
	 * @return Lancamento 
	 */
	public Lancamento buscarPorCodigo(Long codigo) {
		Lancamento lancamento = repository.findOne(codigo);
		return lancamento;
	}
	
}
