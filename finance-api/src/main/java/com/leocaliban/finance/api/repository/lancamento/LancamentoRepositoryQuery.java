package com.leocaliban.finance.api.repository.lancamento;

import java.util.List;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;

/**
 * Interface {@link LancamentoRepositoryQuery} responsável pela criação dos métodos de filtragem 
 * dos lançamentos através do SpringDataJPA
 * @author Leocaliban
 *
 * 3 de mar de 2018
 */
public interface LancamentoRepositoryQuery {
	
	/**
	 * Método que implementa o filtro para a busca de lançamentos.
	 * @param filter classe que traz os parametros que serão filtrados.
	 * @return Lista de lançamentos.
	 */
	public List<Lancamento> filtrar(LancamentoFilter filter);
}
