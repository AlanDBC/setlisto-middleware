package com.setlisto.service.test;

import java.util.ArrayList;
import java.util.List;

import com.setlisto.service.MailService;
import com.setlisto.service.impl.MailServiceImpl;

public class MailServiceTest {
	
	private MailService ms = null;

	public MailServiceTest() {
		ms = new MailServiceImpl();
	}

	public void testEnviarEmail () {
		ms.sendEmail("alanb1213aa@gmail.com", "volamos", "Melo :P");
	}
	
	public void testEnviarEmailMultiple () {
		List<String> destinatarios = new ArrayList<String>();
		destinatarios.add("joseantoniolp.teacher@gmail.com");
		destinatarios.add("sarasantossarmiento@gmail.com");
		destinatarios.add("alanb1213aa@gmail.com");
		destinatarios.add("cenizost16@gmail.com");
		destinatarios.add("xubummitreigro-4523@yopmail.com");
		
		for (String destinatario : destinatarios) {
			ms.sendEmail(destinatario, "volamos", "Melo :P");
		}
		
	}
	
	public static void main(String[] args) {
		MailServiceTest test = new MailServiceTest();
//		test.testEnviarEmail();
		test.testEnviarEmailMultiple();
	}

}
