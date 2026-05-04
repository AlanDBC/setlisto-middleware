package com.setlisto.service.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;
import com.setlisto.service.TicketService;
import com.setlisto.service.impl.TicketServiceImpl;

public class TicketServiceTest {

    private TicketService service = null;

    public TicketServiceTest() {
        this.service = new TicketServiceImpl();
    }

    /**
     * Prueba la creación de un nuevo ticket.
     * Basado en TicketDAOTest: se usa Pago 10 y Plaza 1 (Aitana).
     */
    public void testCreate() {
        System.out.println("--- Test: TicketService.create ---");
        TicketDTO nuevo = new TicketDTO();
        nuevo.setCodigo("QR-SERVICE-TEST-001");
        nuevo.setPrecio(new BigDecimal("55.00"));
        nuevo.setFechaCompra(LocalDateTime.now());
        nuevo.setPagoId(10L);               // Pago de Juan Pérez
        nuevo.setPlazaEventoMusicalId(1L);       // Plaza disponible en evento 1
        nuevo.setTipoTicketId(1L);          // Tipo General

        TicketDTO creado = service.create(nuevo);
        if (creado != null && creado.getId() != null) {
            System.out.println("Ticket emitido con éxito. ID: " + creado.getId());
        } else {
            System.out.println("Error al emitir el ticket.");
        }
    }

    /**
     * Prueba la recuperación de un ticket con toda su información relacionada.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Test: TicketService.findById(" + id + ") ---");
        TicketDTO t = service.findById(id);
        if (t != null) {
            System.out.println("Ticket: " + t.getCodigo() + " para " + t.getEventoNombre());
            System.out.println("  Asistente: " + t.getClienteNombre());
            System.out.println("  Ubicación: " + t.getLugarNombre() + " - Fila " + t.getPlazaFila());
        } else {
            System.out.println("No se encontró el ticket.");
        }
    }

    /**
     * Prueba la verificación de ocupación de una plaza.
     */
    public void testSeatStatus(Long plazaEnEventoId) {
        System.out.println("\n--- Test: TicketService.existsBySeatOfEventId(" + plazaEnEventoId + ") ---");
        boolean ocupada = service.existsBySeatOfEventId(plazaEnEventoId);
        System.out.println(ocupada ? "La plaza ya tiene un ticket (Ocupada)." 
                                  : "La plaza está libre de tickets.");
    }

    /**
     * Prueba el conteo de ventas totales para un evento específico.
     */
    public void testCountByEvent(Long eventId) {
        System.out.println("\n--- Test: TicketService.countByEventoId(" + eventId + ") ---");
        long total = service.countByEventoId(eventId);
        System.out.println("Entradas vendidas para el evento " + eventId + ": " + total);
    }

    /**
     * Prueba la búsqueda por criterios (ej: tickets de un cliente específico).
     */
    public void testFindByCriteria(Long clienteId) {
        System.out.println("\n--- Test: TicketService.findByCriteria (Cliente: " + clienteId + ") ---");
        TicketCriteria criteria = new TicketCriteria();
        criteria.setClienteId(clienteId);
        
        Results<TicketDTO> resultados = service.findByCriteria(criteria, 1, 10);
        List<TicketDTO> tickets = resultados.getPage();
        System.out.println("Se encontraron " + tickets.size() + " tickets para el cliente.");
    }

    public static void main(String[] args) {
        TicketServiceTest test = new TicketServiceTest();

        // 1. Consultar ticket existente (ID 1: Aitana - Juan Pérez)
        test.testFindById(1L);

        // 2. Verificar si la plaza 2 (ya vendida) está ocupada
        test.testSeatStatus(2L);

        // 3. Ver volumen de ventas del evento 1
        test.testCountByEvent(1L);

        // 4. Buscar historial de tickets de Juan (ID 1)
        test.testFindByCriteria(1L);

        // 5. Crear ticket nuevo
        test.testCreate();
    }
}