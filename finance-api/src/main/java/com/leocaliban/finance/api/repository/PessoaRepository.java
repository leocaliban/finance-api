package com.leocaliban.finance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Pessoa;

/**
 * Interface de gerenciamento das Pessoas no banco de dados
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
