package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.TipoEvento;
import com.setlisto.service.TipoEventoService;
import com.setlisto.service.impl.TipoEventoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Tipos de Evento.
 * Valida la recuperación de categorías principales (Concert, Festival, etc.).
 */
public class TipoEventoServiceTest {

    private static final Logger logger = LogManager.getLogger(TipoEventoServiceTest.class);
    private TipoEventoService tipoEventoService;

    public TipoEventoServiceTest() {
        try {
            // Inicialización del servicio transaccional conforme al middleware [3]
            this.tipoEventoService = new TipoEventoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar TipoEventoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un tipo de evento por su ID.
     * Según datos maestros: 1=Concert, 2=Festival, 5=Musical Show [2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y el commit automáticamente [3]
            TipoEvento tipo = tipoEventoService.findById(id);
            if (tipo != null) {
                logger.info("Tipo de evento recuperado: ID={} | Nombre={}", tipo.getId(), tipo.getNombre());
                System.out.println("Resultado: " + tipo.getNombre());
            } else {
                logger.warn("No se encontró el tipo de evento con ID: {}", id);
                System.out.println("Tipo de evento no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los tipos de eventos registrados.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<TipoEvento> tipos = tipoEventoService.findAll();
            if (tipos != null && !tipos.isEmpty()) {
                logger.info("Se han recuperado {} tipos de eventos maestros.", tipos.size());
                System.out.println("Listado de categorías principales:");
                for (TipoEvento te : tipos) {
                    System.out.println(" - " + te.getId() + ": " + te.getNombre());
                }
            } else {
                System.out.println("La lista de tipos de eventos está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los tipos: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TipoEventoServiceTest tester = new TipoEventoServiceTest();

        // 1. Probar "Concert" (ID 1 según datos maestros) [2]
        tester.testFindById(1L);

        // 2. Probar "Musical Show" (ID 5 según datos maestros) [2]
        tester.testFindById(5L);

        // 3. Probar recuperación completa de la tabla event_type [1]
        tester.testFindAll();

        System.out.println("\n--- Pruebas de TipoEventoService finalizadas ---");
    }
}