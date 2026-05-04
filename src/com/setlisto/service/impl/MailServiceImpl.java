package com.setlisto.service.impl;

import java.util.List;

import org.apache.commons.mail.HtmlEmail;

import com.setlisto.service.MailService;


public class MailServiceImpl implements MailService {

	public MailServiceImpl() {
	}

	public void sendEmail(String para, String asunto, String contenido) {
		try {
			//            Email email = new SimpleEmail();
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.addTo(para);
			email.setFrom("SetlistoContact@Gmail.com", "Setlisto");
			email.setSubject(asunto);
			email.setHtmlMsg(contenido);

			email.setSmtpPort(587);
			email.setStartTLSEnabled(true);
			email.setSSLOnConnect(false);
			email.setAuthentication("SetlistoContact@Gmail.com","qmzw pujp ails zrvn");
			email.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendEmail(List<String> clientes, String asunto, String contenido) {
		for (String destinatario: clientes ) {
			sendEmail(destinatario, asunto, contenido);
		}
		System.out.println("Enviado a " + clientes.size() + " destinatarios");
	}

}