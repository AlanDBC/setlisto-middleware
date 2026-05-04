package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.service.SubTipoEventoService;
import com.setlisto.service.impl.SubTipoEventoServiceImpl;

public class SubTipoEventoServiceTest {

    private SubTipoEventoService service = null;

    public SubTipoEventoServiceTest() {
        this.service = new SubTipoEventoServiceImpl();
    }

    /**
     * Prueba la recuperación de un subtipo por su ID.
     * Según los datos maestros, el ID 1 es 'Solo Artist'.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: SubTipoEventoService.findById(" + id + ") ---");
        SubTipoEventoDTO ste = service.findById(id);
        if (ste != null) {
            System.out.println("✔ Subtipo encontrado: " + ste.getNombre());
            System.out.println("  Pertenece al tipo: " + ste.getTipoEventoNombre());
        } else {
            System.out.println("✘ No se encontró el subtipo con ID: " + id);
        }
    }

    /**
     * Prueba la filtración por tipo de evento padre (ej: todos los de 'Festival' ID 2).
     */
    public void testFindByTipoEvento(Long tipoEventoId) {
        System.out.println("\n--- Test: SubTipoEventoService.findByTipoEvento(" + tipoEventoId + ") ---");
        List<SubTipoEventoDTO> lista = service.findByTipoEvento(tipoEventoId);
        if (lista != null && !lista.isEmpty()) {
            System.out.println("✔ Se encontraron " + lista.size() + " subtipos:");
            for (SubTipoEventoDTO ste : lista) {
                System.out.println("  - " + ste.getNombre());
            }
        } else {
            System.out.println("✘ No hay subtipos para el tipo con ID: " + tipoEventoId);
        }
    }


    public static void main(String[] args) {
        SubTipoEventoServiceTest test = new SubTipoEventoServiceTest();

        // 1. Buscar 'Solo Artist' (ID 1)
        test.testFindById(1L);

        // 2. Buscar todos los subtipos de 'Festival' (ID 2)
        test.testFindByTipoEvento(2L);
    }
}