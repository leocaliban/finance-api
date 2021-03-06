package com.leocaliban.finance.api.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Classe {@link Endereco} que representa o Endereço de uma pessoa.
 * Endereço
 * será embarcado em Pessoa.
 * 
 * @author Leocaliban
 *
 *         28 de fev de 2018
 */
@Embeddable
public class Endereco {
	private String rua;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "codigo_cidade")
	private Cidade cidade;

	/**
	 * Método que retorna a rua do endereço.
	 * 
	 * @return rua.
	 */
	public String getRua() {
		return rua;
	}

	/**
	 * Método que atualiza a rua do endereço.
	 * 
	 * @param rua.
	 */
	public void setRua(String rua) {
		this.rua = rua;
	}

	/**
	 * Método que retorna o número.
	 * 
	 * @return numero.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * Método que atualiza o número.
	 * 
	 * @param numero.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * Método que retorna o complemento do endereço.
	 * 
	 * @return complemento.
	 */
	public String getComplemento() {
		return complemento;
	}

	/**
	 * Método que atualiza o complemento do endereço.
	 * 
	 * @param complemento.
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	/**
	 * Método que retorna o bairro do endereço.
	 * 
	 * @return bairro.
	 */
	public String getBairro() {
		return bairro;
	}

	/**
	 * Método que atualiza o bairro do endereço.
	 * 
	 * @return bairro.
	 */
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	/**
	 * Método que retorna o CEP do endereço.
	 * 
	 * @return cep.
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * Método que atualiza o CEP do endereço.
	 * 
	 * @return cep.
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
