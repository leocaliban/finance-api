package com.leocaliban.finance.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.leocaliban.finance.api.config.property.FinanceApiProperty;

/**
 * Classe main para a inicialização da aplicação Spring
 * @author Leocaliban
 *
 * 26 de fev de 2018
 */
@SpringBootApplication
@EnableConfigurationProperties(FinanceApiProperty.class)
public class FinanceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceApiApplication.class, args);
	}
}
