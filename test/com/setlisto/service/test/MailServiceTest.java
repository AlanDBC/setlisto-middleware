package com.setlisto.service.test;

import java.util.ArrayList;
import java.util.List;
import com.setlisto.service.MailService;
import com.setlisto.service.impl.MailServiceImpl;

/**
 * Clase para testear el envío de correos (Confirmaciones, Notificaciones).
 */
public class MailServiceTest {

    private MailService mailService = new MailServiceImpl();

    public void testEnviarEmailIndividual() {
        System.out.println("\n--- Ejecutando testEnviarEmailIndividual ---");
        try {
            mailService.sendEmail("usuario@ejemplo.com", "Bienvenido a Setlisto", 
                                   "Gracias por registrarte en nuestra plataforma.");
            System.out.println("Correo enviado con éxito.");
        } catch (Exception e) {
            System.err.println("Error en envío: " + e.getMessage());
        }
    }

    public void testEnviarEmailMultiple() {
        System.out.println("\n--- Ejecutando testEnviarEmailMultiple ---");
        try {
            List<String> destinatarios = new ArrayList<>();
            destinatarios.add("admin@setlisto.com");
            destinatarios.add("soporte@setlisto.com");
            
            mailService.sendEmail(destinatarios, "Aviso de Sistema", "Prueba de envío múltiple.");
            System.out.println("Correos múltiples enviados.");
        } catch (Exception e) {
            System.err.println("Error en envío múltiple: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MailServiceTest tester = new MailServiceTest();
        tester.testEnviarEmailIndividual();
        tester.testEnviarEmailMultiple();
    }
}