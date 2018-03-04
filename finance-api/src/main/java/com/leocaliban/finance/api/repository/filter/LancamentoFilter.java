package com.leocaliban.finance.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Classe {@link LancamentoFilter} responsável por filtrar as buscas no banco de dados,
 * permitindo filtrar pela descrição atribuída ou entre um período de datas.
 * @author Leocaliban
 *
 * 3 de mar de 2018
 */
public class LancamentoFilter {
	
	private String descricao;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;

	/**
	 * Método que retorna a descrição do filtro. 
	 * @return descricao do filtro.
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Método que atualiza a descrição do filtro.
	 * @param descricao do filtro.
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Método que retorna a data de vencimento do ínicio de um intervalo.
	 * @return data de vencimento inicio para o filtro.
	 */
	public LocalDate getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	/**
	 * Método que atualiza a data de vencimento do ínicio de um intervalo.
	 * @param data de vencimento inicio para o filtro.
	 */
	public void setDataVencimentoDe(LocalDate dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	/**
	 * Método que retorna a data de vencimento do fim de um intervalo.
	 * @return data de vencimento fim para o filtro.
	 */
	public LocalDate getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	/**
	 * Método que atualiza a data de vencimento do fim de um intervalo.
	 * @param data de vencimento fim para o filtro.
	 */
	public void setDataVencimentoAte(LocalDate dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}
}
