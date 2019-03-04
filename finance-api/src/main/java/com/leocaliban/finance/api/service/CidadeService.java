package com.leocaliban.finance.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Cidade;
import com.leocaliban.finance.api.repository.CidadeRepository;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;

	public List<Cidade> listarCidadesDoEstado(Long codigoEstado) {
		return repository.findByEstadoCodigo(codigoEstado);
	}
}
