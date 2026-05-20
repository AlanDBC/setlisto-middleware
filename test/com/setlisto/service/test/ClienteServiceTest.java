package com.setlisto.service.test;

import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;
import com.setlisto.service.ClienteService;
import com.setlisto.service.impl.ClienteServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Clientes.
 * Incluye pruebas de Login (encriptación), Búsqueda por ID y Criterios.
 */
public class ClienteServiceTest {

    private static final Logger logger = LogManager.getLogger(ClienteServiceTest.class);
    private ClienteService clienteService;

    public ClienteServiceTest() {
        try {
            // El servicio requiere internamente EncryptionService y MailService
            this.clienteService = new ClienteServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar ClienteService: {}", e.getMessage());
        }
    }
    
    public void testRegister() {
        System.out.println("\n--- Ejecutando testRegister para Cliente ---");
        try {
            Cliente nuevoCliente = new Cliente();
    
            nuevoCliente.setNombre("Lucía");
            nuevoCliente.setApellido("Méndez");
            nuevoCliente.setEmail("lucia.test@email.com");
            nuevoCliente.setContrasena("user1234"); // Se cifrará en la capa de servicio 
            nuevoCliente.setTelefono("600999888");
            nuevoCliente.setFechaNacimiento(Date.valueOf("1998-10-25"));
            nuevoCliente.setPreferencias("Indie, Rock, Alternative");
            nuevoCliente.setVerificado(false); // Por defecto en el registro inicial
            nuevoCliente.setActivo(true);

            Cliente registrado = clienteService.register(nuevoCliente);

            if (registrado != null && registrado.getId() != null) {
                logger.info("Cliente registrado con éxito. ID: {} | Email: {}", 
                            registrado.getId(), registrado.getEmail());
                System.out.println("Registro completado: " + registrado.getNombre() + " " + registrado.getApellido());
            } else {
                logger.warn("El registro no devolvió un objeto válido.");
            }
        } catch (Exception e) {
            // Captura errores como email duplicado (UNIQUE INDEX en BD) [1]
            logger.error("Error en el registro transaccional de cliente: {}", e.getMessage());
            System.err.println("Fallo al registrar cliente: " + e.getMessage());
        }
    }

    /**
     * Prueba el proceso de autenticación.
     * El servicio debe comparar la clave en texto plano con el hash de la BD [1].
     */
    public void testLogin(String email, String password) {
        System.out.println("\n--- Ejecutando testLogin para: " + email + " ---");
        try {
            Cliente cliente = clienteService.login(email, password);
            if (cliente != null) {
                logger.info("Login exitoso: Bienvenido {} {}", cliente.getNombre(), cliente.getApellido());
                System.out.println("Acceso concedido a: " + cliente.getNombre());
            } else {
                logger.warn("Login fallido para el correo: {}", email);
                System.out.println("Credenciales incorrectas.");
            }
        } catch (Exception e) {
            logger.error("Error en proceso de login: {}", e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación por ID.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            Cliente cliente = clienteService.findById(id);
            if (cliente != null) {
                logger.info("Cliente recuperado: {} (Email: {})", cliente.getNombre(), cliente.getEmail());
                System.out.println("Datos: " + cliente.getNombre() + " " + cliente.getApellido());
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error buscando cliente {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda por criterios (paginada).
     */
    public void testFindByCriteria(String nombreFiltro) {
        System.out.println("\n--- Ejecutando testFindByCriteria (Filtro Nombre: " + nombreFiltro + ") ---");
        try {
            ClienteCriteria criteria = new ClienteCriteria();
            // Suponiendo que el criteria tiene setters para los filtros [4]
             criteria.setNombre(nombreFiltro); 

            // Búsqueda paginada (desde el registro 0, tamaño 10)
            Results<Cliente> resultados = clienteService.findByCriteria(criteria, 0, 10);
            
            if (resultados != null && !resultados.getPage().isEmpty()) {
                System.out.println("Total encontrados: " + resultados.getTotal());
                for (Cliente c : resultados.getPage()) {
                    System.out.println(" - " + c.getNombre() + " " + c.getApellido() + " [" + c.getEmail() + "]");
                }
            } else {
                System.out.println("No se encontraron clientes con esos criterios.");
            }
        } catch (Exception e) {
            logger.error("Error en búsqueda por criterios: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClienteServiceTest tester = new ClienteServiceTest();

        tester.testLogin("sofiatest@gmail.es", "abc123.");

//         2. Probar Login Fallido
//        tester.testLogin("juan.perez@email.com", "clave_incorrecta");

        // 3. Probar búsqueda por ID (ID 2 es Ana García)
//        tester.testFindById(2L);

        // 4. Probar búsqueda general por criterios
//        tester.testFindByCriteria("Ana");
        
        // 5. Registro
        tester.testRegister();

        System.out.println("\n--- Pruebas de ClienteService finalizadas ---");
    }
}