package com.setlisto.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.service.PlazaEnEventoService;
import com.setlisto.service.impl.PlazaEnEventoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Plazas en Eventos.
 * Gestiona el inventario dinámico de asientos para eventos musicales.
 */
public class PlazaEnEventoServiceTest {

    private static final Logger logger = LogManager.getLogger(PlazaEnEventoServiceTest.class);
    private PlazaEnEventoService plazaEnEventoService;

    public PlazaEnEventoServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.plazaEnEventoService = new PlazaEnEventoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar PlazaEnEventoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación detallada de una plaza en un evento por su ID.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio realiza el JOIN con evento, plaza y estado
            PlazaEnEventoDTO plaza = plazaEnEventoService.findById(id);
            if (plaza != null) {
                logger.info("Plaza recuperada: Evento='{}' | Fila={} | Número={} | Estado={}", 
                            plaza.getEventoMusicalNombre(), plaza.getPlazaFila(), 
                            plaza.getPlazaNumero(), plaza.getEstadoPlazaNombre());
                System.out.println("Evento: " + plaza.getEventoMusicalNombre());
                System.out.println("Ubicación: Fila " + plaza.getPlazaFila() + ", Asiento " + plaza.getPlazaNumero());
                System.out.println("Estado actual: " + plaza.getEstadoPlazaNombre());
            } else {
                System.out.println("Plaza no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Error en testFindById para ID {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba si una plaza específica está disponible para la venta (Estado ID 1).
     */
    public void testIsAvailable(Long id) {
        System.out.println("\n--- Ejecutando testIsAvailable para ID: " + id + " ---");
        try {
            boolean disponible = plazaEnEventoService.isSeatAvailable(id);
            logger.info("Verificación de disponibilidad para plaza {}: {}", id, disponible);
            System.out.println("¿Está disponible para la venta?: " + (disponible ? "SÍ" : "NO"));
        } catch (Exception e) {
            logger.error("Error verificando disponibilidad: {}", e.getMessage());
        }
    }

    /**
     * Prueba el conteo de asientos disponibles para un evento específico.
     */
    public void testGetAvailableCount(Long eventId) {
        System.out.println("\n--- Ejecutando getAvailableCount para Evento ID: " + eventId + " ---");
        try {
            int count = plazaEnEventoService.getAvailableCount(eventId);
            logger.info("Total disponibles para evento {}: {}", eventId, count);
            System.out.println("Número de asientos libres: " + count);
        } catch (Exception e) {
            logger.error("Error contando disponibles: {}", e.getMessage());
        }
    }

    /**
     * Prueba la actualización del estado de una plaza (p.ej. de Disponible a Vendido).
     */
    public void testUpdateStatus(Long id, Long newStatusId) {
        System.out.println("\n--- Ejecutando testUpdateStatus para ID: " + id + " a Estado: " + newStatusId + " ---");
        try {
            boolean actualizado = plazaEnEventoService.updateStatus(id, newStatusId);
            if (actualizado) {
                logger.info("Estado de la plaza {} actualizado a {}", id, newStatusId);
                System.out.println("Cambio de estado realizado con éxito.");
            }
        } catch (Exception e) {
            logger.error("Error actualizando estado: {}", e.getMessage());
            System.err.println("Fallo transaccional: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PlazaEnEventoServiceTest tester = new PlazaEnEventoServiceTest();

        // 1. Probar plaza disponible (ID 1: Evento Aitana, Asiento 1) [2]
        tester.testFindById(1L);
        tester.testIsAvailable(1L);

        // 2. Probar plaza ya vendida (ID 2: Evento Aitana, Asiento 2) [2]
        tester.testFindById(2L);
        tester.testIsAvailable(2L);

        // 3. Contar disponibles para el evento de Aitana (ID 1)
        tester.testGetAvailableCount(1L);

        // 4. Simular venta: Cambiar plaza 1 a 'Vendido' (Estado 2) [3]
        tester.testUpdateStatus(1L, 2L);

        System.out.println("\n--- Pruebas de PlazaEnEventoService finalizadas ---");
    }
}