package com.leocaliban.finance.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe {@link Permissao} que representa a permissão de acesso de um usuário.
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@Entity
@Table(name = "permissao")
public class Permissao {

	@Id
	private Long codigo;
	private String descricao;
	
	/**
	 * Método que retorna o código da permissão. 
	 * @return codigo da permissão
	 */
	public Long getCodigo() {
		return codigo;
	}
	
	/**
	 * Método que atualiza o código da permissão. 
	 * @param codigo da permissão.
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Método que retorna a descrição da permição. 
	 * @return descrição da permição.
	 */
	public String getDescricao() {
		return descricao;
	}
	
	/**
	 * Método que atualiza a descrição da permição. 
	 * @param descrição da permição.
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		Permissao other = (Permissao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
