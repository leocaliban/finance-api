package com.leocaliban.finance.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Estado;
import com.leocaliban.finance.api.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;

	public List<Estado> listarTodos() {
		return repository.findAll();
	}
}
