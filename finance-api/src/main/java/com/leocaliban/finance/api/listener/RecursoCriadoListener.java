package com.leocaliban.finance.api.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.leocaliban.finance.api.event.RecursoCriadoEvent;

/**
 * Classe {@link RecursoCriadoListener} representa o ouvinte do evento RecursoCriadoListener
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
@Component //Determina a classe como componente do Spring
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	/**
	 * Quando o evento for disparado este método do listener irá executar o código.
	 */
	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
		HttpServletResponse response = recursoCriadoEvent.getResponse();
		Long codigo = recursoCriadoEvent.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
	}
	
	/**
	 * Método privado que adiciona o Header Location
	 * @param response resposta http
	 * @param codigo código do objeto da requisição
	 */
	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		//Montando a URI da requisição atual
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		//Criando o Header de retorno, que indica onde o recurso salvo poderá pode ser acessado (REST)
		response.setHeader("Location", uri.toASCIIString());
	}
}
