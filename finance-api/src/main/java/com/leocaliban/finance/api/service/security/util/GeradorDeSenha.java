package com.leocaliban.finance.api.service.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Leocaliban
 *
 * 5 de mar de 2018
 */
public class GeradorDeSenha {
	
	public static void main(String[]args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
	}
}
