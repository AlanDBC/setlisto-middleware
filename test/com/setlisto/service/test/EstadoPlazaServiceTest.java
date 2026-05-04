package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.EstadoPlaza;
import com.setlisto.service.EstadoPlazaService;
import com.setlisto.service.impl.EstadoPlazaServiceImpl;

public class EstadoPlazaServiceTest {

    private EstadoPlazaService service = null;

    public EstadoPlazaServiceTest() {
        this.service = new EstadoPlazaServiceImpl();
    }

    /**
     * Prueba el método findById para verificar la recuperación de un estado específico.
     * Según las fuentes, los IDs válidos suelen ser 1 (AVAILABLE), 2 (SOLD) o 3 (DISABLED)
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: EstadoPlazaService.findById(" + id + ") ---");
        EstadoPlaza ep = service.findById(id);
        if (ep != null) {
            System.out.println("Estado encontrado: " + ep);
        } else {
            System.out.println("No se encontró el estado con ID: " + id);
        }
    }

    /**
     * Prueba el método findAll para obtener todos los estados de plaza registrados.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: EstadoPlazaService.findAll() ---");
        List<EstadoPlaza> estados = service.findAll();
        if (estados != null && !estados.isEmpty()) {
            System.out.println("Se encontraron " + estados.size() + " estados de plaza:");
            for (EstadoPlaza ep : estados) {
                System.out.println("  - " + ep);
            }
        } else {
            System.out.println("No se recuperaron estados de plaza.");
        }
    }

    public static void main(String[] args) {
        EstadoPlazaServiceTest test = new EstadoPlazaServiceTest();

        // 1. Probar con ID 1 (AVAILABLE según la lógica del sistema)
        test.testFindById(1L);

        // 2. Listar todos los estados
        test.testFindAll();
    }
}