package com.leocaliban.finance.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.repository.lancamento.LancamentoRepositoryQuery;

/**
 * Interface de gerenciamento dos Lançamentos no banco de dados
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{
	
	public List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);

}
