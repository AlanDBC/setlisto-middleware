package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.Pais;
import com.setlisto.service.PaisService;
import com.setlisto.service.impl.PaisServiceImpl;

public class PaisServiceTest {

    private PaisService service = null;

    public PaisServiceTest() {
        this.service = new PaisServiceImpl();
    }

    /**
     * Prueba la recuperación de un país por su ID.
     * Según los datos maestros, el ID 15 corresponde a 'España'.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: PaisService.findById(" + id + ") ---");
        Pais pais = service.findById(id);
        if (pais != null) {
            System.out.println("País encontrado: " + pais.getNombre());
        } else {
            System.out.println("No se encontró el país con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de la lista completa de países registrados.
     * La lista debe venir ordenada alfabéticamente por nombre según el DAO.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: PaisService.findAll() ---");
        List<Pais> paises = service.findAll();
        if (paises != null && !paises.isEmpty()) {
            System.out.println("Se encontraron " + paises.size() + " países:");
            for (Pais p : paises) {
                System.out.println("  - " + p.getNombre());
            }
        } else {
            System.out.println("No se recuperaron países de la base de datos.");
        }
    }

    public static void main(String[] args) {
        PaisServiceTest test = new PaisServiceTest();

        // 1. Probar con ID 15 (España)
        test.testFindById(15L);

        // 2. Listar todos los países de Europa cargados en el sistema
        test.testFindAll();
    }
}