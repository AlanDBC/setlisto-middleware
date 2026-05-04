package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.GeneroMusical;
import com.setlisto.service.GeneroMusicalService;
import com.setlisto.service.impl.GeneroMusicalServiceImpl;

public class GeneroMusicalServiceTest {

    private GeneroMusicalService service = null;

    public GeneroMusicalServiceTest() {
        // Inicialización de la implementación del servicio
        this.service = new GeneroMusicalServiceImpl();
    }

    /**
     * Prueba la recuperación de un género específico por su ID.
     * Basado en los datos maestros, el ID 1 corresponde a 'Pop' y el 2 a 'Rock'.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: GeneroMusicalService.findById(" + id + ") ---");
        GeneroMusical gm = service.findById(id);
        if (gm != null) {
            System.out.println("Género encontrado: " + gm.getNombre());
        } else {
            System.out.println("No se encontró el género con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de la lista completa de géneros musicales.
     * Debería devolver los 14 géneros iniciales definidos en el sistema.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: GeneroMusicalService.findAll() ---");
        List<GeneroMusical> generos = service.findAll();
        if (generos != null && !generos.isEmpty()) {
            System.out.println("Se recuperaron " + generos.size() + " géneros musicales:");
            for (GeneroMusical gm : generos) {
                System.out.println("  - " + gm.getNombre());
            }
        } else {
            System.out.println("No se encontraron géneros musicales.");
        }
    }

    public static void main(String[] args) {
        GeneroMusicalServiceTest test = new GeneroMusicalServiceTest();

        // 1. Probar búsqueda por ID (ID 2 es Rock según datos maestros)
        test.testFindById(2L);

        // 2. Listar todos los géneros
        test.testFindAll();
    }
}