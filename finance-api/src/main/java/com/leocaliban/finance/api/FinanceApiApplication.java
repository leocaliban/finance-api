package com.leocaliban.finance.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import com.leocaliban.finance.api.config.property.FinanceApiProperty;

/**
 * Classe main para a inicialização da aplicação Spring
 * 
 * @author Leocaliban
 *
 *         26 de fev de 2018
 */
@SpringBootApplication
@EnableConfigurationProperties(FinanceApiProperty.class)
public class FinanceApiApplication {

	private static ApplicationContext APPLICATION_CONTEXT;

	public static void main(String[] args) {
		APPLICATION_CONTEXT = SpringApplication.run(FinanceApiApplication.class, args);
	}

	public static <T> T getBean(Class<T> type) {
		return APPLICATION_CONTEXT.getBean(type);
	}
}
