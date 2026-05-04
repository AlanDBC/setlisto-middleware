package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.TipoEvento;
import com.setlisto.service.TipoEventoService;
import com.setlisto.service.impl.TipoEventoServiceImpl;

public class TipoEventoServiceTest {

    private TipoEventoService service = null;

    public TipoEventoServiceTest() {
        // Inicialización de la implementación del servicio
        this.service = new TipoEventoServiceImpl();
    }

    /**
     * Prueba la recuperación de un tipo de evento por su ID.
     * Según los datos maestros, el ID 1 corresponde a 'Concert'.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: TipoEventoService.findById(" + id + ") ---");
        TipoEvento te = service.findById(id);
        if (te != null) {
            System.out.println("Tipo de evento encontrado: " + te.getNombre());
        } else {
            System.out.println("No se encontró el tipo de evento con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de todos los tipos de eventos principales.
     * Debería devolver los 9 tipos definidos en el sistema.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: TipoEventoService.findAll() ---");
        List<TipoEvento> tipos = service.findAll();
        if (tipos != null && !tipos.isEmpty()) {
            System.out.println("Se encontraron " + tipos.size() + " tipos de eventos:");
            for (TipoEvento te : tipos) {
                System.out.println("  - " + te.getNombre());
            }
        } else {
            System.out.println("No se recuperaron tipos de eventos.");
        }
    }
    
    public static void main(String[] args) {
        TipoEventoServiceTest test = new TipoEventoServiceTest();

        // 1. Probar con ID 1 (Concert)
        test.testFindById(1L);

        // 2. Listar todos los tipos maestros (Concert, Festival, Club Night, etc.)
        test.testFindAll();
    }
}