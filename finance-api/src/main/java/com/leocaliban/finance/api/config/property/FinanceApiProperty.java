package com.leocaliban.finance.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Classe {@link FinanceApiProperty} de configuração 
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
@ConfigurationProperties("finance")
public class FinanceApiProperty {
	
	private String originPermitida = "http://localhost:4200";
	//private String originPermitida = "http://localhost:8100";

	private final Seguranca seguranca = new Seguranca();

	public static class Seguranca{
	
		private boolean enableHttps;
	
		public boolean isEnableHttps() {
			return enableHttps;
		}
	
		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
	}
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
	
	public Seguranca getSeguranca() {
		return seguranca;
	}
}
