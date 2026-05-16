package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.TipoTicket;
import com.setlisto.service.TipoTicketService;
import com.setlisto.service.impl.TipoTicketServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Tipos de Ticket.
 * Valida la recuperación de categorías de entradas (General, VIP, etc.).
 */
public class TipoTicketServiceTest {

    private static final Logger logger = LogManager.getLogger(TipoTicketServiceTest.class);
    private TipoTicketService tipoTicketService;

    public TipoTicketServiceTest() {
        try {
            // Inicialización del servicio transaccional [2]
            this.tipoTicketService = new TipoTicketServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar TipoTicketService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un tipo de ticket por ID.
     * Según datos maestros: 1=General, 2=VIP [1].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión mediante JDBCUtils [2]
            TipoTicket tipo = tipoTicketService.findById(id);
            if (tipo != null) {
                logger.info("Tipo de ticket recuperado: ID={} | Nombre={}", tipo.getId(), tipo.getNombre());
                System.out.println("Resultado: " + tipo.getNombre());
            } else {
                System.out.println("Tipo de ticket no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error en búsqueda por ID {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los tipos de tickets disponibles.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<TipoTicket> tipos = tipoTicketService.findAll();
            if (tipos != null && !tipos.isEmpty()) {
                System.out.println("Listado de tipos de tickets:");
                for (TipoTicket tt : tipos) {
                    System.out.println(" - " + tt.getId() + ": " + tt.getNombre());
                }
            }
        } catch (Exception e) {
            logger.error("Error al recuperar listado completo: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        TipoTicketServiceTest tester = new TipoTicketServiceTest();

        // 1. Probar "General" (ID 1)
        tester.testFindById(1L);

        // 2. Probar recuperación completa
        tester.testFindAll();

        System.out.println("\n--- Pruebas de TipoTicketService finalizadas ---");
    }
}