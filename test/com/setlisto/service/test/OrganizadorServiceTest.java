package com.setlisto.service.test;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Organizador;
import com.setlisto.service.OrganizadorService;
import com.setlisto.service.impl.OrganizadorServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Organizadores.
 * Incluye registro (transaccional con cifrado), login y búsquedas.
 */
public class OrganizadorServiceTest {

    private static final Logger logger = LogManager.getLogger(OrganizadorServiceTest.class);
    private OrganizadorService organizadorService;

    public OrganizadorServiceTest() {
        try {
            // Inicialización coherente con el middleware (usa EncryptionService y MailService internamente)
            this.organizadorService = new OrganizadorServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar OrganizadorService: {}", e.getMessage());
        }
    }

    /**
     * Prueba el registro de un nuevo organizador.
     * El servicio debe cifrar la contraseña antes de persistir [1].
     */
    public void testRegister() {
        System.out.println("\n--- Ejecutando testRegister ---");
        try {
            Organizador org = new Organizador();
            org.setNombreComercial("Producciones Test S.L.");
            org.setNombre("Carlos");
            org.setApellido1("García");
            org.setApellido2("López");
            org.setEmail("contacto@producciones.test");
            org.setContrasena("admin123"); // Se cifrará en el ServiceImpl
            org.setTelefono("912345678");
            org.setFechaNacimiento(Date.valueOf("1985-05-20"));
            org.setVerificado(false);

            Organizador registrado = organizadorService.register(org);
            if (registrado != null && registrado.getId() != null) {
                logger.info("Organizador registrado con éxito. ID: {}", registrado.getId());
                System.out.println("Registro completado para: " + registrado.getNombreComercial());
            }
        } catch (Exception e) {
            logger.error("Error en el registro transaccional: {}", e.getMessage());
            System.err.println("Fallo al registrar: " + e.getMessage());
        }
    }

    /**
     * Prueba el proceso de login para organizadores.
     */
    public void testLogin(String email, String pass) {
        System.out.println("\n--- Ejecutando testLogin para: " + email + " ---");
        try {
            // El servicio verifica el hash BCrypt almacenado [3]
            Organizador org = organizadorService.login(email, pass);
            if (org != null) {
                logger.info("Login exitoso para: {}", org.getNombreComercial());
                System.out.println("Acceso concedido a la cuenta de: " + org.getNombreComercial());
            } else {
                logger.warn("Credenciales inválidas para: {}", email);
                System.out.println("Login fallido.");
            }
        } catch (Exception e) {
            logger.error("Error en autenticación: {}", e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda por ID basada en datos de prueba [2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            Organizador org = organizadorService.findById(id);
            if (org != null) {
                System.out.println("Empresa: " + org.getNombreComercial());
                System.out.println("Contacto: " + org.getNombre() + " " + org.getApellido1());
            } else {
                System.out.println("Organizador no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error buscando organizador {}: {}", id, e.getMessage());
        }
    }

    public static void main(String[] args) {
        OrganizadorServiceTest tester = new OrganizadorServiceTest();

        // 1. Probar registro (Transacción de escritura)
        tester.testRegister();

        // 2. Probar Login con datos de prueba (ID 1: Live Nation) [2]
        tester.testLogin("contacto@producciones.test", "admin123");

        // 3. Probar recuperación por ID
//        tester.testFindById(1L);

        System.out.println("\n--- Pruebas de OrganizadorService finalizadas ---");
    }
}