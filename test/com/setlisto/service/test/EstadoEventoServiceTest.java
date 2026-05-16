package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.EstadoEvento;
import com.setlisto.service.EstadoEventoService;
import com.setlisto.service.impl.EstadoEventoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Estados de Evento.
 * Se enfoca en la recuperación de estados maestros (Borrador, Programado, etc.).
 */
public class EstadoEventoServiceTest {

    private static final Logger logger = LogManager.getLogger(EstadoEventoServiceTest.class);
    private EstadoEventoService estadoEventoService;

    public EstadoEventoServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.estadoEventoService = new EstadoEventoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar EstadoEventoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un estado por ID.
     * Según datos maestros: 1=Borrador, 2=Programado, etc. [3]
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y el commit de forma interna [4]
            EstadoEvento estado = estadoEventoService.findById(id);
            if (estado != null) {
                logger.info("Estado recuperado: ID={} | Nombre={}", estado.getId(), estado.getNombre());
                System.out.println("Resultado: " + estado.getNombre());
            } else {
                logger.warn("No se encontró el estado con ID: {}", id);
                System.out.println("Estado no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID: {}", e.getMessage());
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación del listado completo de estados.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<EstadoEvento> estados = estadoEventoService.findAll();
            if (estados != null && !estados.isEmpty()) {
                logger.info("Se han recuperado {} estados maestros.", estados.size());
                System.out.println("Lista de estados de evento:");
                for (EstadoEvento ee : estados) {
                    System.out.println(" - " + ee.getId() + ": " + ee.getNombre());
                }
            } else {
                System.out.println("La lista de estados está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda total: {}", e.getMessage());
            System.err.println("Error al recuperar estados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EstadoEventoServiceTest tester = new EstadoEventoServiceTest();

        // 1. Probar "Programado"
        tester.testFindById(2L);

        // 2. Probar id inexistente
        tester.testFindById(44L);

        // 3. Probar recuperación de toda la tabla event_status
        tester.testFindAll();

        System.out.println("\n--- Pruebas de EstadoEventoService finalizadas ---");
    }
}