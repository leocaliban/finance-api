package com.leocaliban.finance.api.exceptionhandler.util;

/**
 * Classe {@link Erro} é utilitária para salvar as mensagens de erro que serão exibidas nas 
 * capturas de exceções da classe FinanceExceptionHandler
 * 
 * @author Leocaliban
 *
 * 27 de fev de 2018
 */
public class Erro {
	
	private String mensagemUsuario;
	
	private String mensagemDesenvolvedor;

	public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}

	/**
	 * Método que retorna a mensagemUsuario
	 * @return mensagemUsuario do erro
	 */
	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	/**
	 * Método que atualiza a mensagemUsuario
	 * @param mensagemUsuario do erro
	 */
	public void setMensagemUsuario(String mensagemUsuario) {
		this.mensagemUsuario = mensagemUsuario;
	}

	/**
	 * Método que retorna a mensagemDesenvolvedor
	 * @return mensagemDesenvolvedor do erro
	 */
	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}

	/**
	 * Método que atualiza a mensagemDesenvolvedor
	 * @param mensagemDesenvolvedor do erro
	 */
	public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
	
}
