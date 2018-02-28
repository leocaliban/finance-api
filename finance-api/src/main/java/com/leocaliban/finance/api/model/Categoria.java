package com.leocaliban.finance.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe {@link Categoria} que representa uma categoria
 * @author Leocaliban
 *
 * 26 de fev de 2018
 */
@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	@Size(min = 3, max = 30)
	private String nome;

	/**
	 * Método que retorna o Código da categoria. 
	 * @return codigo da categoria
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método que atualiza o Código da categoria
	 * @param codigo da categoria
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Método que retorna o Nome da categoria
	 * @return nome da categoria
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que atualiza o Nome da categoria
	 * @param nome da categoria
	 */
	public void setNome(String nome) {
		this.nome = nome;
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
		Categoria other = (Categoria) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
