package com.leocaliban.finance.api.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Classe {@link Usuario} que representa um usuário do sistema.
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	private Long codigo;
	
	private String nome;
	private String email;
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario"), 
										inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
	private List<Permissao> permissoes;

	/**
	 * Método que retorna o código do usuário. 
	 * @return codigo do usuário.
	 */
	public Long getCodigo() {
		return codigo;
	}

	/**
	 * Método que atualiza o código do usuário. 
	 * @param codigo do usuário.
	 */
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	/**
	 * Método que retorna o nome do usuário. 
	 * @return nome do usuário.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método que atualiza o nome do usuário. 
	 * @param nome do usuário.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método que retorna o email do usuário. 
	 * @return email do usuário.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Método que atualiza o email do usuário. 
	 * @param email do usuário.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Método que retorna a senha do usuário. 
	 * @return senha do usuário.
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * Método que atualiza a senha do usuário. 
	 * @param senha do usuário.
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Método que retorna a lista de permissões do usuário. 
	 * @return permissoes do usuário.
	 */
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	/**
	 * Método que atualiza a lista de permissões do usuário. 
	 * @param permissoes do usuário.
	 */
	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}		
}
