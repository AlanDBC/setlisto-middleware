package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.MetodoPago;
import com.setlisto.service.MetodoPagoService;
import com.setlisto.service.impl.MetodoPagoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Métodos de Pago.
 * Valida la recuperación de medios de pago maestros (VISA, PayPal, Bizum, etc.).
 */
public class MetodoPagoServiceTest {

    private static final Logger logger = LogManager.getLogger(MetodoPagoServiceTest.class);
    private MetodoPagoService metodoPagoService;

    public MetodoPagoServiceTest() {
        try {
            // Inicialización del servicio transaccional [2]
            this.metodoPagoService = new MetodoPagoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar MetodoPagoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un método de pago por su ID.
     * Según datos maestros: 1=VISA, 3=PayPal, 4=Bizum [1].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y la transacción internamente [2]
            MetodoPago metodo = metodoPagoService.findById(id);
            if (metodo != null) {
                logger.info("Método de pago recuperado: ID={} | Nombre={}", metodo.getId(), metodo.getNombre());
                System.out.println("Resultado: " + metodo.getNombre());
            } else {
                logger.warn("No se encontró el método de pago con ID: {}", id);
                System.out.println("Método de pago no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los métodos de pago disponibles.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<MetodoPago> metodos = metodoPagoService.findAll();
            if (metodos != null && !metodos.isEmpty()) {
                logger.info("Se han recuperado {} métodos de pago.", metodos.size());
                System.out.println("Métodos de pago registrados en el sistema:");
                for (MetodoPago mp : metodos) {
                    System.out.println(" - " + mp.getId() + ": " + mp.getNombre());
                }
            } else {
                System.out.println("La lista de métodos de pago está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los métodos: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MetodoPagoServiceTest tester = new MetodoPagoServiceTest();

        // 1. Probar "VISA" (ID 1 según datos maestros)
        tester.testFindById(1L);

        // 2. Probar id inexistente
        tester.testFindById(66L);

        // 3. Probar recuperación de toda la tabla payment_method
        tester.testFindAll();

        System.out.println("\n--- Pruebas de MetodoPagoService finalizadas ---");
    }
}