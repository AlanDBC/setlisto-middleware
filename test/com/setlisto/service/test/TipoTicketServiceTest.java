package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.TipoTicket;
import com.setlisto.service.TipoTicketService;
import com.setlisto.service.impl.TipoTicketServiceImpl;

public class TipoTicketServiceTest {

    private TipoTicketService service = null;

    public TipoTicketServiceTest() {
        // Inicialización de la implementación del servicio
        this.service = new TipoTicketServiceImpl();
    }

    /**
     * Prueba la recuperación de un tipo de ticket específico por ID.
     * Según los datos maestros: 1=General, 2=VIP, 3=Pista, 4=Grada.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: TipoTicketService.findById(" + id + ") ---");
        TipoTicket tt = service.findById(id);
        if (tt != null) {
            System.out.println("Tipo de ticket encontrado: " + tt.getNombre());
        } else {
            System.out.println("No se encontró el tipo de ticket con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de todas las categorías de tickets configuradas.
     * Debería devolver exactamente 4 registros según el script de carga.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: TipoTicketService.findAll() ---");
        List<TipoTicket> lista = service.findAll();
        if (lista != null && !lista.isEmpty()) {
            System.out.println("Se encontraron " + lista.size() + " tipos de tickets:");
            for (TipoTicket tt : lista) {
                System.out.println("  - ID: " + tt.getId() + " | Nombre: " + tt.getNombre());
            }
        } else {
            System.out.println("No se recuperaron tipos de tickets de la base de datos.");
        }
    }

    public static void main(String[] args) {
        TipoTicketServiceTest test = new TipoTicketServiceTest();

        // 1. Probar con ID 2 (VIP según datos maestros)
        test.testFindById(2L);

        // 2. Listar todos los tipos (General, VIP, Pista, Grada)
        test.testFindAll();
    }
}