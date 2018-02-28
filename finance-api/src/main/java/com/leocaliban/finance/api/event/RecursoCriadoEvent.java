package com.leocaliban.finance.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * Classe {@link RecursoCriadoEvent} utilitária para criar o Header Location a partir de eventos
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
public class RecursoCriadoEvent extends ApplicationEvent{
	private static final long serialVersionUID = 1L;

	private HttpServletResponse response;
	private Long codigo;
	
	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}

	/**
	 * Método que retorna a resposta http
	 * @return response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * Método que retorna o Código do objeto acionado pelo evento. 
	 * @return codigo.
	 */
	public Long getCodigo() {
		return codigo;
	}
	
}
