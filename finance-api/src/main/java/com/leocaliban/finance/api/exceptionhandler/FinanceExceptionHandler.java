package com.leocaliban.finance.api.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leocaliban.finance.api.exceptionhandler.util.ErroMessageNotReadable;

/**
 * Classe {@link FinanceExceptionHandler} que irá capturar exceções de respostas das entidades.
 * @author Leocaliban
 *
 * 27 de fev de 2018
 */
@ControllerAdvice //indica que é um controlador que observa toda a aplicação.
public class FinanceExceptionHandler extends ResponseEntityExceptionHandler{
	
	//Pelo MessageSource o Spring localiza a messages.properties
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Gera duas mensagens, uma para o cliente e uma técnica para o desenvolvedor
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause().toString();
		
		ErroMessageNotReadable erro = new ErroMessageNotReadable(mensagemUsuario, mensagemDesenvolvedor);

		return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}
}
