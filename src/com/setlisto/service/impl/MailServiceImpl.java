package com.setlisto.service.impl;

import java.util.List;

import org.apache.commons.mail.HtmlEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.service.MailException;
import com.setlisto.service.MailService;


public class MailServiceImpl implements MailService {
	
	private static final Logger logger = LogManager.getLogger(MailServiceImpl.class.getName());

	public MailServiceImpl() {
	}

	public void sendEmail(String para, String asunto, String contenido) throws MailException {
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
			logger.error("Error al enviar correo a {}: {}", para, e.getMessage());
            throw new MailException(e);
		}
	}

	@Override
	public void sendEmail(List<String> clientes, String asunto, String contenido) throws MailException {
		for (String destinatario: clientes ) {
			sendEmail(destinatario, asunto, contenido);
		}
		System.out.println("Enviado a " + clientes.size() + " destinatarios");
	}

}