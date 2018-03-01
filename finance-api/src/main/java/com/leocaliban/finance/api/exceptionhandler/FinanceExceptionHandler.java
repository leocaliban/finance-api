package com.leocaliban.finance.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.leocaliban.finance.api.exceptionhandler.util.Erro;

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
	
	/**
	 * Faz a captura da exceção causada por atributos que não podem ser lidos
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		//Gera duas mensagens, uma para o cliente e uma técnica para o desenvolvedor
		String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		
		Erro erro = new Erro(mensagemUsuario, mensagemDesenvolvedor);

		return handleExceptionInternal(ex, erro, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Faz a captura da exceção causada por argumentos que não puderam ser validados
	 * Salva em uma lista para apresentar as mensagens para todos os elementos inválidos
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Erro> erros = criarListaDeErros(ex.getBindingResult());
		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * Método privado que cria uma lista de erros
	 * @param bindingResult contém todos os campos com erros capturados
	 * @return lista de erros
	 */
	private List<Erro> criarListaDeErros(BindingResult bindingResult){
		List<Erro> erros = new ArrayList<>();
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}
		return erros;
	}
	
	/**
	 * Método que trata a exceção EmptyResultDataAccessException que ocorre quando se tenta excluir um recurso que não existe
	 * @param ex exceção
	 * @param request requisição
	 * @return entidade de resposta para o usuário
	 */
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessExeption(EmptyResultDataAccessException ex, WebRequest request){
		String mensagemUsuario = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
