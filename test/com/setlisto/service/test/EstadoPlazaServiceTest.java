package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.EstadoPlaza;
import com.setlisto.service.EstadoPlazaService;
import com.setlisto.service.impl.EstadoPlazaServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Estados de Plaza (Asientos).
 * Valida la recuperación de estados maestros de la tabla seat_status.
 */
public class EstadoPlazaServiceTest {

    private static final Logger logger = LogManager.getLogger(EstadoPlazaServiceTest.class);
    private EstadoPlazaService estadoPlazaService;

    public EstadoPlazaServiceTest() {
        try {
            // Inicialización del servicio transaccional [3]
            this.estadoPlazaService = new EstadoPlazaServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar EstadoPlazaService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un estado de plaza por su ID.
     * Según datos maestros: 1=Disponible, 2=Vendido, 3=Deshabilitado [1, 2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona internamente la conexión y la transacción [3]
            EstadoPlaza estado = estadoPlazaService.findById(id);
            if (estado != null) {
                logger.info("Estado de plaza recuperado: ID={} | Nombre={}", estado.getId(), estado.getNombre());
                System.out.println("Resultado: " + estado.getNombre());
            } else {
                logger.warn("No se encontró el estado de plaza con ID: {}", id);
                System.out.println("Estado de plaza no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los estados de plaza registrados.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<EstadoPlaza> estados = estadoPlazaService.findAll();
            if (estados != null && !estados.isEmpty()) {
                logger.info("Se han recuperado {} estados de plaza.", estados.size());
                System.out.println("Lista de estados de plaza disponibles:");
                for (EstadoPlaza ep : estados) {
                    System.out.println(" - " + ep.getId() + ": " + ep.getNombre());
                }
            } else {
                System.out.println("La lista de estados de plaza está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los estados: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EstadoPlazaServiceTest tester = new EstadoPlazaServiceTest();

        // 1. Probar "Disponible" (ID 1 según datos maestros [2])
        tester.testFindById(1L);

        // 2. Probar "Vendido" (ID 2 según datos maestros [2])
        tester.testFindById(2L);

        // 3. Probar recuperación de toda la tabla seat_status
        tester.testFindAll();

        System.out.println("\n--- Pruebas de EstadoPlazaService finalizadas ---");
    }
}