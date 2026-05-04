package com.setlisto.service.test;

import java.sql.Date;
import java.util.List;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;
import com.setlisto.service.ClienteService;
import com.setlisto.service.impl.ClienteServiceImpl;

public class ClienteServiceTest {

    private ClienteService service = null;

    public ClienteServiceTest() {
        this.service = new ClienteServiceImpl();
    }

    /**
     * Prueba el registro de un nuevo cliente. 
     * Debe cifrar la contraseña y enviar un correo de bienvenida.
     */
    public void testRegister() {
        System.out.println("--- Test: ClienteService.register ---");
        Cliente nuevo = new Cliente();
        nuevo.setNombre("Test");
        nuevo.setApellido("Service");
        nuevo.setEmail("test.service@ejemplo.com");
        nuevo.setContrasena("secreto123");
        nuevo.setTelefono("555123488");
        nuevo.setActivo(true);
        nuevo.setVerificado(false);
        Date fecha = Date.valueOf("1990-01-01");
        nuevo.setFechaNacimiento(fecha);

        Cliente registrado = service.register(nuevo);
        if (registrado != null && registrado.getId() != null) {
            System.out.println("Cliente registrado con ID: " + registrado.getId());
        } else {
            System.out.println("El registro falló (posible email duplicado).");
        }
    }

    /**
     * Prueba el proceso de login verificando el cifrado de la contraseña.
     */
    public void testLogin(String email, String password) {
        System.out.println("\n--- Test: ClienteService.login ---");
        Cliente cliente = service.login(email, password);
        if (cliente != null) {
            System.out.println("Login exitoso para: " + cliente.getNombre());
        } else {
            System.out.println("Credenciales incorrectas para: " + email);
        }
    }

    /**
     * Prueba la actualización de la contraseña validando la anterior.
     */
    public void testUpdatePassword(Long id, String oldPass, String newPass) {
        System.out.println("\n--- Test: ClienteService.updatePassword ---");
        boolean exito = service.updatePassword(id, oldPass, newPass);
        System.out.println(exito ? "Contraseña actualizada correctamente." : "Error al actualizar (password vieja incorrecta).");
    }

    /**
     * Prueba la búsqueda dinámica por criterios (ej: clientes activos).
     */
    public void testFindByCriteria() {
        System.out.println("\n--- Test: ClienteService.findBy (Activos) ---");
        ClienteCriteria criteria = new ClienteCriteria();
        criteria.setActivo(true);
//        criteria.setNombre("Test");
        
        Results<Cliente> resultados = service.findByCriteria(criteria, 1, 10);
        List<Cliente> clientes = resultados.getPage();
        System.out.println("Se encontraron " + clientes.size() + " clientes activos.");
    }

    public static void main(String[] args) {
        ClienteServiceTest test = new ClienteServiceTest();

        // 1. Probar registro
        test.testRegister();

        // 2. Probar login (usar credenciales de un cliente existente o el recién creado)
//        test.testLogin("test.service@ejemplo.com", "secreto123");

        // 3. Probar búsqueda
//        test.testFindByCriteria();

        // 4. Probar cambio de password (ejemplo con ID 11L)
//         test.testUpdatePassword(11L, "nueva123", "nuevicima123");
    }
}