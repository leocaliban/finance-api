package com.leocaliban.finance.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Classe {@link SecurityConfig} responsável pela configuração de segurança do SpringSecurity. 
 * @author Leocaliban
 *
 * 4 de mar de 2018
 */
@Configuration //Identifica a classe como classe de configuração.
@EnableWebSecurity //habilita a segurança.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ROLE");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/categorias").permitAll() //permite acesso sem autenticação para /categorias
			.anyRequest() //qualquer requisição
			.authenticated() //deve estar altenticado para acessar
			.and().httpBasic() //tipo de autenticação
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().csrf(); 
	}
}
