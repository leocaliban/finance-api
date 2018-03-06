package com.leocaliban.finance.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	 * Método que recupera todas as pessoas do banco de dados através do repository.
	 * @param nome nome da pessoa.
	 * @param pageable paginação.
	 * @return página de pessoas.
	 */
	public Page<Pessoa> listar(String nome, Pageable pageable){
		return repository.findByNomeContaining(nome, pageable);
	}
	
	/**
	 * Edita uma pessoa do banco de dados pelo código através do repository
	 * @param codigo da pessoa que será editada
	 * @param pessoa objeto Pessoa que será editado
	 * @return pessoa editada
	 */
	public Pessoa editar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		
		//Propriedade do spring que aplica os dados atualizados 'pessoa' na entidade que foi editada 'pessoaSalva' ignorando o 'codigo'
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}
	
	/**
	 * Método que faz uma edição parcial no atributo 'Ativo' da Pessoa 
	 * @param codigo codigo da pessoa que será editada
	 * @param ativo boolean que será atribuido na pessoa
	 */
	public void editarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}

	/**
	 * Método privado que busca uma pessoa no banco de dados pelo código
	 * @param codigo código da pessoa
	 * @return Pessoa buscada
	 */
	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoa = repository.findOne(codigo);
		
		if(pessoa == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoa;
	}
}
