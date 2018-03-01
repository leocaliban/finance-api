package com.leocaliban.finance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Lancamento;

/**
 * Interface de gerenciamento dos Lançamentos no banco de dados
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
