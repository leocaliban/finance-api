package com.leocaliban.finance.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Classe {@link Lancamento} que representa um lançamento
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
@Entity
@Table(name = "lancamento")
public class Lancamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	private String descricao;
	
	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;
	
	@NotNull
	private BigDecimal valor;
	
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;

	/**
	 * Método que retorna o Código do lançamento. 
	 * @return codigo do lançamento
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método que atualiza o Código do lançamento.
	 * @param codigo do lançamento.
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método que retorna a descrição do lançamento. 
	 * @return descrição do lançamento.
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Método que atualiza a descrição do lançamento. 
	 * @param descrição do lançamento. 
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método que retorna a data de vencimento do lançamento. 
	 * @return data de vencimento do lançamento. 
	 */
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	/**
	 * Método que atualiza a data de vencimento do lançamento. 
	 * @param data de vencimento do lançamento. 
	 */
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	/**
	 * Método que retorna a data do pagamento do lançamento. 
	 * @return data do pagamento do lançamento.  
	 */
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	/**
	 * Método que atualiza a data do pagamento do lançamento.  
	 * @param data do pagamento do lançamento. 
	 */
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	/**
	 * Método que retorna o valor do lançamento.
	 * @return valor do lançamento.
	 */
	public BigDecimal getValor() {
		return valor;
	}

	/**
	 * Método que atualiza o valor do lançamento.
	 * @param valor do lançamento.
	 */
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	/**
	 * Método que retorna a observação do lançamento.
	 * @return observação do lançamento.
	 */
	public String getObservacao() {
		return observacao;
	}

	/**
	 * Método que atualiza a observação do lançamento.
	 * @param observação do lançamento.
	 */
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	/**
	 * Método que retorna o tipo do lançamento.
	 * @return tipo do lançamento.
	 */
	public TipoLancamento getTipo() {
		return tipo;
	}

	/**
	 * Método que atualiza o tipo do lançamento.
	 * @param tipo do lançamento.
	 */
	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	/**
	 * Método que retorna a categoria do lançamento.
	 * @return categoria do lançamento.
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Método que atualiza a categoria do lançamento.
	 * @param categoria do lançamento.
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * Método que retorna a pessoa do lançamento.
	 * @return pessoa do lançamento.
	 */
	public Pessoa getPessoa() {
		return pessoa;
	}

	/**
	 * Método que atualiza a pessoa do lançamento.
	 * @param pessoa do lançamento.
	 */
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	
}
