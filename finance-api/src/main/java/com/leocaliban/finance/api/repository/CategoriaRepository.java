package com.leocaliban.finance.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Categoria;

/**
 * Interface de gerenciamento das Categorias no banco de dados
 * @author Leocaliban
 *
 * 26 de fev de 2018
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
