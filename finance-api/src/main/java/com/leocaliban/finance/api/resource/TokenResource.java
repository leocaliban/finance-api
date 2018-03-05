package com.leocaliban.finance.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leocaliban.finance.api.config.property.FinanceApiProperty;

/**
 * Classe {@link TokenResource} responsável por eliminar o cookie que contém o refreshToken para realização de logout do sistema.
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private FinanceApiProperty property;

	/**
	 * Método que retira o valor do refreshToken contido no cookie e aplica a expiração instantânea do cookie.
	 * @param req requisição 
	 * @param res resposta
	 */
	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse res) {
		Cookie cookie = new Cookie("refreshTokenCookie", null); //Nome do cookie e token
		cookie.setHttpOnly(true);
		cookie.setSecure(property.getSeguranca().isEnableHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0); //expiração do cookie
		res.addCookie(cookie);
		res.setStatus(HttpStatus.NO_CONTENT.value());
		
	}
}
