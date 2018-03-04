package com.leocaliban.finance.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Classe {@link AuthorizationServerConfig} responsável pela configuração de segurança do Servidor de Autorização.
 * @author Leocaliban
 *
 * 4 de mar de 2018
 */
@Configuration //Identifica a classe como classe de configuração.
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager; //recupera e valida o usuário e senha.
	
	/**
	 * Configura a autorização do cliente.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("angular") //nick cliente que consumirá a api
			.secret("angular") //senha do cliente
			.scopes("read", "write") //escopo de limitação de acesso
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(20) //duração de atividade do token de acesso
			.refreshTokenValiditySeconds(3600 * 24); //duração de atividade do refresh token
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore())
			.accessTokenConverter(accessTokenConverter())
			.reuseRefreshTokens(false) //ao solicitar um token pelo refresh, o token não será reutilizado
			.authenticationManager(authenticationManager);
	}
	
	/**
	 * Converte o token
	 * @return token de acesso convertido
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("leocaliban"); //senha que faz a validação dos tokens
		return accessTokenConverter;
	}
	
	/**
	 * Armazena os tokens
	 * @return token JWT
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

}
