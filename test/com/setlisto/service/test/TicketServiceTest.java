package com.setlisto.service.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;
import com.setlisto.service.TicketService;
import com.setlisto.service.impl.TicketServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Tickets.
 * Valida la emisión de entradas, búsquedas por código QR y conteo de aforo.
 */
public class TicketServiceTest {

    private static final Logger logger = LogManager.getLogger(TicketServiceTest.class);
    private TicketService ticketService;

    public TicketServiceTest() {
        try {
            // Inicialización del servicio transaccional conforme al middleware [3]
            this.ticketService = new TicketServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar TicketService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un ticket por su ID.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            TicketDTO ticket = ticketService.findById(id);
            if (ticket != null) {
                logger.info("Ticket recuperado: {} (Precio: {}€)", ticket.getCodigo(), ticket.getPrecio());
                System.out.println("Código QR: " + ticket.getCodigo());
                System.out.println("Precio: " + ticket.getPrecio() + "€");
            } else {
                System.out.println("Ticket no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error buscando ticket {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba la validación de un ticket mediante su código único (QR).
     */
    public void testFindByCode(String code) {
        System.out.println("\n--- Ejecutando testFindByCode para: " + code + " ---");
        try {
            TicketDTO ticket = ticketService.findByCode(code);
            if (ticket != null) {
                logger.info("Ticket validado por código: ID {}", ticket.getId());
                System.out.println("Validación exitosa. ID Ticket: " + ticket.getId());
            } else {
                System.out.println("El código QR proporcionado no es válido.");
            }
        } catch (Exception e) {
            logger.error("Error validando código {}: {}", code, e.getMessage());
        }
    }
    /**
     * Prueba la busqueda de tickets por codigo contenido en
     */
    public void testFindByCodeLike(String code) {
        System.out.println("\n--- Ejecutando testFindByCode para: " + code + " ---");
        try {
            List<TicketDTO> tickets = ticketService.findByCodeLike(code);
            if (tickets != null) {
            	System.out.println("Tickets encontrados: " + tickets.size());
            }
            else {
            	System.out.println("No se encontro ningun ticket");
            }
        } catch (Exception e) {
            logger.error("Error validando código {}: {}", code, e.getMessage());
        }
    }

    /**
     * Prueba el conteo de tickets emitidos para un evento musical específico.
     */
    public void testCountByEvento(Long eventId) {
        System.out.println("\n--- Ejecutando countByEventoId para Evento ID: " + eventId + " ---");
        try {
            // El servicio consulta el total de entradas vendidas para el evento [1]
            long total = ticketService.countByEventoId(eventId);
            logger.info("Total de entradas para evento {}: {}", eventId, total);
            System.out.println("Entradas emitidas actualmente: " + total);
        } catch (Exception e) {
            logger.error("Error en el conteo de tickets: {}", e.getMessage());
        }
    }

    /**
     * Prueba la creación (emisión) de un nuevo ticket.
     */
    public void testCreate() {
        System.out.println("\n--- Ejecutando testCreate (Simulando compra) ---");
        try {
            TicketDTO nuevo = new TicketDTO();
            nuevo.setCodigo("QR-TEST-NUEVO-2026");
            nuevo.setPrecio(new BigDecimal("75.00"));
            nuevo.setFechaCompra(LocalDateTime.now());
            nuevo.setPagoId(10L); // Pago aprobado de Juan Pérez
            nuevo.setPlazaEventoMusicalId(14L); // Plaza en Afterlife (Disponible)
            nuevo.setTipoTicketId(1L); // Tipo General

            TicketDTO creado = ticketService.create(nuevo);
            if (creado != null && creado.getId() != null) {
                logger.info("Ticket emitido correctamente con ID: {}", creado.getId());
                System.out.println("Nuevo ticket generado: " + creado.getCodigo());
            }
        } catch (Exception e) {
            // Captura errores si la plaza ya tiene un ticket (Unique constraint [7])
            logger.error("Fallo al emitir ticket transaccional: {}", e.getMessage());
            System.err.println("Excepción en emisión: " + e.getMessage());
        }
    }
    
    public void testFindByCriteria() {
        System.out.println("\n--- Ejecutando testFindByCriteria (Búsqueda Parcial) ---");
        try {
            String codigoParcial = "QR";
            TicketCriteria criteria = new TicketCriteria();
            criteria.setCodigo(codigoParcial);
            
            Results<TicketDTO> results = ticketService.findByCriteria(criteria, 0, 20);
            
            if (results == null) {
                System.err.println("ERROR: El objeto Results es nulo.");
                return;
            }

            System.out.println("Resultados totales encontrados en BD: " + results.getTotal());
            System.out.println("Resultados devueltos en esta página: " + (results.getPage() != null ? results.getPage().size() : 0));

            List<TicketDTO> tickets = results.getPage();
            
            if (tickets == null || tickets.isEmpty()) {
                System.out.println("ADVERTENCIA: No se encontraron registros que coincidan con '" + codigoParcial + "' en la base de datos actual.");
            } else {
                System.out.println("ÉXITO: Se recuperaron " + tickets.size() + " registros.");
                
                int errores = 0;
                for (TicketDTO ticket : tickets) {
                    String codigoBD = ticket.getCodigo();
                    
                    if (codigoBD == null) {
                        System.err.println("ERROR: Se encontró un ticket con ID " + ticket.getId() + " pero su código es NULO.");
                        errores++;
                        continue;
                    }
                    
                    // Forzamos mayúsculas para comparar de forma segura (case-insensitive)
                    if (!codigoBD.toUpperCase().contains(codigoParcial.toUpperCase())) {
                        System.err.println("ERROR: El código '" + codigoBD + "' (ID: " + ticket.getId() + ") no contiene la cadena '" + codigoParcial + "'.");
                        errores++;
                    } else {
                        System.out.println("Ticket verificado correcto -> ID: " + ticket.getId() + " | Código: " + codigoBD);
                    }
                }
                
                if (errores == 0) {
                    System.out.println("COMPLETADO: Todos los registros cumplen con el criterio del LIKE %" + codigoParcial + "%.");
                } else {
                    System.err.println("FALLO: Hay " + errores + " registros que no deberían estar en el resultado.");
                }
            }
            
        } catch (Exception e) {
            logger.error("Fallo crítico al buscar por criteria: {}", e.getMessage(), e);
            System.err.println("Excepción capturada en el test: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TicketServiceTest tester = new TicketServiceTest();

        // 1. Probar búsqueda de ticket existente (ID 1: Juan Pérez en Aitana [2])
//        tester.testFindById(1L);

        // 2. Probar búsqueda por código QR (ID 2: Ana en Madama Butterfly [2])
//        tester.testFindByCode("TCK-C0206A0");

        // 3. Contar tickets del concierto de Aitana (Evento ID 1 [8])
//        tester.testCountByEvento(1L);

        // 4. Probar emisión de un nuevo ticket transaccional
//        tester.testCreate();
        
        // Probar busqueda por codigo contenido en
//        tester.testFindByCodeLike("QR");
        
        tester.testFindByCriteria();

        System.out.println("\n--- Pruebas de TicketService finalizadas ---");
    }
}