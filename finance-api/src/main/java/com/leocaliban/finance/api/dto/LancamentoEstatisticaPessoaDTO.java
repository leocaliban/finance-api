package com.leocaliban.finance.api.dto;

import java.math.BigDecimal;

import com.leocaliban.finance.api.model.Pessoa;
import com.leocaliban.finance.api.model.TipoLancamento;

public class LancamentoEstatisticaPessoaDTO {

	private TipoLancamento tipo;

	private Pessoa pessoa;

	private BigDecimal total;

	public LancamentoEstatisticaPessoaDTO(TipoLancamento tipo, Pessoa pessoa, BigDecimal total) {
		super();
		this.tipo = tipo;
		this.pessoa = pessoa;
		this.total = total;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
