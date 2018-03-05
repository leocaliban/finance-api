package com.leocaliban.finance.api.service.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.leocaliban.finance.api.model.Usuario;
import com.leocaliban.finance.api.repository.UsuarioRepository;

/**
 * 
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = repository.findByEmail(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new User(email, usuario.getSenha(), getPermissoes(usuario));
	}

	/**
	 * Busca todas as permissões do usuário
	 * @param usuario
	 * @return permissões do usuário
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		usuario.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
}
