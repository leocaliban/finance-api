package com.leocaliban.finance.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.leocaliban.finance.api.model.Usuario;

/**
 * Interface de gerenciamento das Usuários no banco de dados
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	public Optional<Usuario> findByEmail(String email);

}
