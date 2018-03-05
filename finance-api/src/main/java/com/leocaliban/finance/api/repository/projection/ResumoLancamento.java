package com.leocaliban.finance.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.leocaliban.finance.api.model.TipoLancamento;

/**
 * Classe {@link ResumoLancamento} dá acesso aos atributos necessários para a criação de uma projeção de Resumo de Lançamentos.
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
public class ResumoLancamento {
	
	private Long codigo;
	private String descricao;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private TipoLancamento tipo;
	private String categoria;
	private String pessoa;
	
	/**
	 * Construtor para atribuir todos os valores na criação do resumo.
	 * @param codigo do lançamento.
	 * @param descricao do lançamento.
	 * @param dataVencimento do lançamento.
	 * @param dataPagamento do lançamento.
	 * @param valor do lançamento.
	 * @param tipo do lançamento.
	 * @param categoria do lançamento.
	 * @param pessoa do lançamento.
	 */
	public ResumoLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
			BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.pessoa = pessoa;
	}
	
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
	public String getCategoria() {
		return categoria;
	}

	/**
	 * Método que atualiza a categoria do lançamento.
	 * @param categoria do lançamento.
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	/**
	 * Método que retorna a pessoa do lançamento.
	 * @return pessoa do lançamento.
	 */
	public String getPessoa() {
		return pessoa;
	}

	/**
	 * Método que atualiza a pessoa do lançamento.
	 * @param pessoa do lançamento.
	 */
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
}
