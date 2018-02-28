package com.leocaliban.finance.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.repository.PessoaRepository;

/**
 * Classe {@link PessoaService} é responsável pelas regras de negócio que envolvem Pessoa
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
@Service //Indica ao spring que essa classe pode ser injetada
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	/**
	 * Edita uma pessoa do banco de dados pelo código através do repository
	 * @param codigo da pessoa que será editada
	 * @param pessoa objeto Pessoa que será editado
	 * @return pessoa editada
	 */
	public Pessoa editar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = repository.findOne(codigo);
		
		if(pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		//Propriedade do spring que aplica os dados atualizados 'pessoa' na entidade que foi editada 'pessoaSalva' ignorando o 'codigo'
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}
}
