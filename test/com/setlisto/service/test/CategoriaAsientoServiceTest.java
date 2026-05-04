package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.CategoriaAsiento;
import com.setlisto.service.CategoriaAsientoService;
import com.setlisto.service.impl.CategoriaAsientoServiceImpl;

public class CategoriaAsientoServiceTest {

    private CategoriaAsientoService service = null;

    public CategoriaAsientoServiceTest() {
        this.service = new CategoriaAsientoServiceImpl();
    }

    /**
     * Prueba el método findById para verificar que recupera una categoría específica
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: CategoriaAsientoService.findById(" + id + ") ---");
        CategoriaAsiento ca = service.findById(id);
        if (ca != null) {
            System.out.println("Categoría encontrada: " + ca);
        } else {
            System.out.println("No se encontró ninguna categoría con ID: " + id);
        }
    }

    /**
     * Prueba el método findAll para obtener el listado completo de categorías
     */
    public void testFindAll() {
        System.out.println("\n--- Test: CategoriaAsientoService.findAll() ---");
        List<CategoriaAsiento> categorias = service.findAll();
        if (categorias != null && !categorias.isEmpty()) {
            System.out.println("Se encontraron " + categorias.size() + " categorías:");
            for (CategoriaAsiento ca : categorias) {
                System.out.println("  - " + ca);
            }
        } else {
            System.out.println("No hay categorías registradas o hubo un error.");
        }
    }

    public static void main(String[] args) {
        CategoriaAsientoServiceTest test = new CategoriaAsientoServiceTest();

        // 1. Probar búsqueda por un ID que sepamos que existe (ejemplo: 1L)
        test.testFindById(1L);

        // 2. Probar listado completo
        test.testFindAll();
    }
}