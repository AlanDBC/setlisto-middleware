package com.setlisto.service;

import java.util.List;

public interface MailService {

	/**
	 * Envia un email a un destinatario
	 * @param para destinatario
	 * @param asunto titulo mail
	 * @param contenido informacion
	 */
	public void sendEmail(String para, String asunto, String contenido) throws MailException ;

	/**
	 * Envia un email a un varios destinatarios
	 * @param clientes a los que se va a enviar el mail
	 * @param asunto titulo mail
	 * @param contenido informacion
	 */
	public void sendEmail(List<String> clientes, String asunto, String contenido) throws MailException ;
}
