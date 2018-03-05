package com.leocaliban.finance.api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.filter.LancamentoFilter;
import com.leocaliban.finance.api.repository.projection.ResumoLancamento;

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
	 * E define a páginação de lançamentos.
	 * @param filter classe que traz os parametros que serão filtrados.
	 * @param pageable paginação
	 * @return Páginas de lançamentos.
	 */
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	
	/**
	 * Método que implementa o resumo do lançamento
	 * @param filter classe que traz os parametros que serão filtrados.
	 * @param pageable paginação
	 * @return Páginas de lançamentos resumidos.
	 */
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
}
