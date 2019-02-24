package com.leocaliban.finance.api.mail;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender mailSender;
	
	// Evento disparado quando a aplicação está pronta para testar o envio de e-mail
//	@EventListener
//	public void teste(ApplicationReadyEvent event) {
//		this.enviarEmail("financeapi2019@gmail.com", 
//		Arrays.asList("nazzawd@gmail.com"), "Teste de mensagem", "Essa mensagem foi enviada pela FINANCE API");
//	}

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
}
