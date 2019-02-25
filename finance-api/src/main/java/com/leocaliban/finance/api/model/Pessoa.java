package com.leocaliban.finance.api.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Classe {@link Pessoa} que representa uma pessoa
 * @author Leocaliban
 *
 * 28 de fev de 2018
 */
@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	@Size(min = 8, max = 50)
	private String nome;
	
	@NotNull
	private boolean ativo;
	
	@Embedded
	private Endereco endereco;
	
	@JsonIgnoreProperties("pessoa")
	@Valid
	@OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL,
		orphanRemoval = true)
	private List<Contato>contatos;

	/**
	 * Método que retorna o Código da pessoa. 
	 * @return codigo da pessoa
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método que atualiza o Código da pessoa.
	 * @param codigo da pessoa
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método que retorna o nome da pessoa. 
	 * @return nome da pessoa
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que atualiza o nome da pessoa.
	 * @param nome da pessoa
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método indica se a pessoa está ativa ou não 
	 * @return true se a pessoa está ativa e false, caso contrário.
	 */
	public boolean isAtivo() {
		return ativo;
	}

	/**
	 * Método atualiza se a pessoa está ativa ou não 
	 * @param true se a pessoa está ativa e false, caso contrário.
	 */
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	/**
	 * Método indica se a pessoa está inativa ou não 
	 * @return true se a pessoa está inativa e false, caso contrário.
	 */
	@JsonIgnore //Indica ao jackson não considerar o método como propriedade serializável.
	@Transient //Indica ao hibernate desconsiderar o método ao salvar no banco.
	public boolean isInativo() {
		return !this.ativo;
	}
	
	/**
	 * Método que retorna o Endereço da pessoa. 
	 * @return endereço da pessoa.
	 */
	public Endereco getEndereco() {
		return endereco;
	}

	/**
	 * Método que atualiza o Endereço da pessoa.
	 * @param endereço da pessoa.
	 */
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
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
		Pessoa other = (Pessoa) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
