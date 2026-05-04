package com.setlisto.service.test;

import java.util.List;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaEnEventoService;
import com.setlisto.service.impl.PlazaEnEventoServiceImpl;

public class PlazaEnEventoServiceTest {

    private PlazaEnEventoService service = null;

    public PlazaEnEventoServiceTest() {
        this.service = new PlazaEnEventoServiceImpl();
    }

    /**
     * Prueba la recuperación de una plaza específica en un evento.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: PlazaEnEventoService.findById(" + id + ") ---");
        PlazaEnEventoDTO dto = service.findById(id);
        if (dto != null) {
            System.out.println("✔ Plaza encontrada: Evento '" + dto.getEventoMusicalNombre() + 
                               "', Fila: " + dto.getPlazaFila() + ", Número: " + dto.getPlazaNumero());
            System.out.println("  Estado actual: " + dto.getEstadoPlazaNombre());
        } else {
            System.out.println("✘ No se encontró la plaza con ID: " + id);
        }
    }

    /**
     * Prueba si una plaza está disponible (Estado ID 1).
     */
    public void testIsAvailable(Long id) {
        System.out.println("\n--- Test: PlazaEnEventoService.isSeatAvailable(" + id + ") ---");
        boolean available = service.isSeatAvailable(id);
        System.out.println(available ? "✔ La plaza está DISPONIBLE." : "✘ La plaza NO está disponible.");
    }

    /**
     * Prueba el conteo de plazas disponibles para un evento.
     */
    public void testGetAvailableCount(Long eventId) {
        System.out.println("\n--- Test: PlazaEnEventoService.getAvailableCount(Evento: " + eventId + ") ---");
        int count = service.getAvailableCount(eventId);
        System.out.println("✔ Plazas disponibles para el evento " + eventId + ": " + count);
    }

    /**
     * Prueba la actualización del estado de una plaza.
     * Cambiaremos una plaza a 'SOLD' (ID 2).
     */
    public void testUpdateStatus(Long plazaId, Long nuevoEstadoId) {
        System.out.println("\n--- Test: PlazaEnEventoService.updateStatus ---");
        boolean exito = service.updateStatus(plazaId, nuevoEstadoId);
        System.out.println(exito ? "✔ Estado actualizado a " + nuevoEstadoId + " para la plaza " + plazaId 
                                 : "✘ Error al actualizar estado.");
    }

    /**
     * Prueba la búsqueda por criterios (ej: plazas vendidas de un evento).
     */
    public void testFindByCriteria(Long eventId, Long statusId) {
        System.out.println("\n--- Test: PlazaEnEventoService.findByCriteria ---");
        PlazaEnEventoCriteria criteria = new PlazaEnEventoCriteria();
        criteria.setEventoMusicalId(eventId);
        criteria.setEstatusId(statusId);

        Results<PlazaEnEventoDTO> resultados = service.findByCriteria(criteria, 1, 10);
        List<PlazaEnEventoDTO> plazas = resultados.getPage();
        System.out.println("✔ Se encontraron " + plazas.size() + " plazas con esos criterios.");
    }

    public static void main(String[] args) {
        PlazaEnEventoServiceTest test = new PlazaEnEventoServiceTest();

        // 1. Consultar plaza 1 (Aitana - Pista - Available según script)
        test.testFindById(1L);

        // 2. Verificar disponibilidad de la plaza 1
//        test.testIsAvailable(1L);

        // 3. Contar disponibles para el evento 1 (Aitana)
//        test.testGetAvailableCount(1L);

        // 4. Buscar todas las plazas vendidas (Status 2) del evento 1
//        test.testFindByCriteria(1L, 2L);

        // 5. Simular venta: Actualizar plaza 1 de Available a Sold (ID 2)
//        test.testUpdateStatus(1L, 2L);
    }
}