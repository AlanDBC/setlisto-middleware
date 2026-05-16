package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.EstadoPago;
import com.setlisto.service.EstadoPagoService;
import com.setlisto.service.impl.EstadoPagoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Estados de Pago.
 * Valida la recuperación de estados maestros de la tabla payment_status.
 */
public class EstadoPagoServiceTest {

    private static final Logger logger = LogManager.getLogger(EstadoPagoServiceTest.class);
    private EstadoPagoService estadoPagoService;

    public EstadoPagoServiceTest() {
        try {
            // Inicialización del servicio transaccional [2]
            this.estadoPagoService = new EstadoPagoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar EstadoPagoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un estado de pago por su ID.
     * Según datos maestros: 1=Pendiente, 2=Aprobado, 3=Rechazado [1].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y el cierre transaccional [2]
            EstadoPago estado = estadoPagoService.findById(id);
            if (estado != null) {
                logger.info("Estado de pago recuperado: ID={} | Nombre={}", estado.getId(), estado.getNombre());
                System.out.println("Resultado: " + estado.getNombre());
            } else {
                logger.warn("No se encontró el estado de pago con ID: {}", id);
                System.out.println("Estado de pago no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los estados de pago registrados.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<EstadoPago> estados = estadoPagoService.findAll();
            if (estados != null && !estados.isEmpty()) {
                logger.info("Se han recuperado {} estados de pago.", estados.size());
                System.out.println("Lista de estados de pago disponibles:");
                for (EstadoPago ep : estados) {
                    System.out.println(" - " + ep.getId() + ": " + ep.getNombre());
                }
            } else {
                System.out.println("La lista de estados de pago está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los estados: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EstadoPagoServiceTest tester = new EstadoPagoServiceTest();

        // 1. Probar "Aprobado" (ID 2 según datos maestros [1])
        tester.testFindById(2L);

        // 2. Probar id inexistente
        tester.testFindById(55L);

        // 3. Probar recuperación de toda la tabla payment_status
        tester.testFindAll();

        System.out.println("\n--- Pruebas de EstadoPagoService finalizadas ---");
    }
}