package com.leocaliban.finance.api.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.leocaliban.finance.api.model.Lancamento;
import com.leocaliban.finance.api.model.Usuario;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateThymeleaf;
	
	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String mensagem) {

		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(remetente);
			helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
			helper.setSubject(assunto);
			helper.setText(mensagem, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			throw new RuntimeException("Erro no envio de e-mail", e);
		}
	}

	public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template,
			Map<String, Object> variaveis) {

		Context context = new Context(new Locale("pt", "BR"));

		variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));

		String mensagem = templateThymeleaf.process(template, context);

		this.enviarEmail(remetente, destinatarios, assunto, mensagem);
	}
	
	public void notificarLancamentosVencidos(List<Lancamento>vencidos, List<Usuario>destinatarios) {
		Map<String, Object>variaveis = new HashMap<>();
		variaveis.put("lancamentos", vencidos);
		
		List<String>emails = destinatarios.stream().map(u -> u.getEmail()).collect(Collectors.toList());
		
		this.enviarEmail("financeapi2019@gmail.com", emails, "Lançamentos Vencidos - FinanceAPI", 
				"mail/aviso-lancamentos-vencidos", variaveis);
	}
}
