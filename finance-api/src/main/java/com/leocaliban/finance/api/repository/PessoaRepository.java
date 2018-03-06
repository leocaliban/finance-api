package com.leocaliban.finance.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Pessoa;

/**
 * Interface de gerenciamento das Pessoas no banco de dados
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	/**
	 * Recupera do banco uma pessoa que contenha o nome do parâmetro.
	 * @param nome nome da pessoa.
	 * @param pageable paginação.
	 * @return página de pessoas.
	 */
	public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable);
}
