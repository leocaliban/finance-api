package com.leocaliban.finance.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Categoria;
import com.leocaliban.finance.api.repository.CategoriaRepository;

/**
 * Classe {@link CategoriaService} é responsável pelas regras de negócio que envolvem Categoria
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@Service //Indica ao spring que essa classe pode ser injetada
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	/**
	 * Edita uma categoria do banco de dados pelo código através do repository
	 * @param codigo da categoria que será editada
	 * @param categoria objeto Categoria que será editado
	 * @return categoria editada
	 */
	public Categoria editar(Long codigo, Categoria categoria) {
		Categoria categoriaSalva = repository.findOne(codigo);
		
		if(categoriaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		//Propriedade do spring que aplica os dados atualizados 'categoria' na entidade que foi editada 'categoriaSalva' ignorando o 'codigo'
		BeanUtils.copyProperties(categoria, categoriaSalva, "codigo");
		return repository.save(categoriaSalva);
	}
}
