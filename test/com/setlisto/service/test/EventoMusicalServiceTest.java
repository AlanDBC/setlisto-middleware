package com.setlisto.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.Results;
import com.setlisto.model.Artista;
import com.setlisto.service.EventoMusicalService;
import com.setlisto.service.impl.EventoMusicalServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Eventos Musicales.
 * Valida la recuperación detallada de eventos (DTO) y búsquedas por criterios.
 */
public class EventoMusicalServiceTest {

    private static final Logger logger = LogManager.getLogger(EventoMusicalServiceTest.class);
    private EventoMusicalService eventoService;

    public EventoMusicalServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.eventoService = new EventoMusicalServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar EventoMusicalService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un evento detallado por ID.
     * Según datos de prueba, el ID 1 es "Aitana: Alpha Tour".
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio realiza el JOIN y GROUP_CONCAT internamente para llenar el DTO [3]
            EventoMusicalDTO evento = eventoService.findById(id);
            
            if (evento != null) {
                logger.info("Evento recuperado: {}", evento.getNombre());
                System.out.println("Nombre: " + evento.getNombre());
                System.out.println("Lugar: " + evento.getLugarNombre());
                System.out.println("Estado: " + evento.getEstadoNombre());
                
                // Mostrar artistas vinculados (recuperados mediante la lógica del DAO) [1]
                if (evento.getArtistas() != null) {
                    System.out.print("Artistas: ");
                    for (Artista a : evento.getArtistas()) {
                        System.out.print(a.getNombre() + " ");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("Evento no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error buscando evento {}: {}", id, e.getMessage(), e);
            System.err.println("Excepción: " + e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda dinámica mediante criterios.
     */
    public void testFindByCriteria() {
        System.out.println("\n--- Ejecutando testFindByCriteria ---");
        try {
            EventoMusicalCriteria criteria = new EventoMusicalCriteria();
            
            criteria.setGeneroMusicalId(2l);
            
            // Búsqueda paginada: primera página, 5 resultados
            Results<EventoMusicalDTO> resultados = eventoService.findByCriteria(criteria, 0, 5);
            
            if (resultados != null && !resultados.getPage().isEmpty()) {
                System.out.println("Eventos encontrados: " + resultados.getTotal());
                for (EventoMusicalDTO e : resultados.getPage()) {
                    System.out.println(" - [" + e.getId() + "] " + e.getNombre() + " en " + e.getLugarNombre());
                }
            } else {
                System.out.println("No se encontraron eventos para los criterios aplicados.");
            }
        } catch (Exception e) {
            logger.error("Error en búsqueda por criterios: {}", e.getMessage(), e);
            System.err.println("Excepción: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventoMusicalServiceTest tester = new EventoMusicalServiceTest();

        // 1. Probar con un evento existente (ID 1: Aitana) [2]
        tester.testFindById(1L);

        // 2. Probar con ID 2 (Pearl Jam: Dark Matter Tour) [2]
        tester.testFindById(2L);

        // 3. Probar búsqueda general por criterios
        tester.testFindByCriteria();

        System.out.println("\n--- Pruebas de EventoMusicalService finalizadas ---");
    }
}