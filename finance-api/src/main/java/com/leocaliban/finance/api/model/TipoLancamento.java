package com.leocaliban.finance.api.model;

/**
 * Enum que representa os Tipos de Lançamentos possíveis no sistema.
 * @author Leocaliban
 *
 * 1 de mar de 2018
 */
public enum TipoLancamento {
	
	RECEITA("Receita"),
	DESPESA("Despesa");
	
	private final String descricao;
	
	TipoLancamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
