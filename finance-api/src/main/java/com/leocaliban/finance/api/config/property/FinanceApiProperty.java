package com.leocaliban.finance.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Classe {@link FinanceApiProperty} de configuração
 * 
 * @author Leocaliban
 *
 *         5 de mar de 2018
 */
@ConfigurationProperties("finance")
public class FinanceApiProperty {

	private String originPermitida = "http://localhost:4200";
	// private String originPermitida = "http://localhost:8100";

	private final Seguranca seguranca = new Seguranca();

	private final Mail mail = new Mail();
	
	private final S3 s3 = new S3();

	public S3 getS3() {
		return s3;
	}

	public Mail getMail() {
		return mail;
	}

	public static class Seguranca {

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

	public static class Mail {
		private String host;

		private Integer port;

		private String username;

		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class S3 {
		private String accessKeyId;
		private String secretAccessKey;
		private String bucket = "leocaliban-finance-arquivos";

		public String getAccessKeyId() {
			return accessKeyId;
		}

		public void setAccessKeyId(String accessKeyId) {
			this.accessKeyId = accessKeyId;
		}

		public String getSecretAccessKey() {
			return secretAccessKey;
		}

		public void setSecretAccessKey(String secretAccessKey) {
			this.secretAccessKey = secretAccessKey;
		}

		public String getBucket() {
			return bucket;
		}

		public void setBucket(String bucket) {
			this.bucket = bucket;
		}

	}
}
