package com.leocaliban.finance.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.leocaliban.finance.api.config.property.FinanceApiProperty;

/**
 * Classe {@link CorsFilter} responsável pela configuração do CORS.
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {
	
	@Autowired
	private FinanceApiProperty property;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest requisicao = (HttpServletRequest) request;
		HttpServletResponse resposta = (HttpServletResponse) response;
		
		resposta.setHeader("Access-Control-Allow-Origin", property.getOriginPermitida());
		resposta.setHeader("Access-Control-Allow-Credentials", "true");
		
		if("OPTIONS".equals(requisicao.getMethod()) && property.getOriginPermitida().equals(requisicao.getHeader("Origin"))) {
			resposta.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			resposta.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resposta.setHeader("Access-Control-Max-Age", "3600");
			
			resposta.setStatus(HttpServletResponse.SC_OK);
		}
		else {
			chain.doFilter(request, response);
		}
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void destroy() {	
	}
}
