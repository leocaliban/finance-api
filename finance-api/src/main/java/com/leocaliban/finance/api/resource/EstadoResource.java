package com.leocaliban.finance.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.leocaliban.finance.api.model.Estado;
import com.leocaliban.finance.api.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;

	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public List<Estado> listar() {
		return service.listarTodos();
	}
}
