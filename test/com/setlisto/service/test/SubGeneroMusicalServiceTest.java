package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.service.SubGeneroMusicalService;
import com.setlisto.service.impl.SubGeneroMusicalServiceImpl;

public class SubGeneroMusicalServiceTest {

    private SubGeneroMusicalService service = null;

    public SubGeneroMusicalServiceTest() {
        this.service = new SubGeneroMusicalServiceImpl();
    }

    /**
     * Prueba la recuperación de un subgénero por su ID.
     * El DTO resultante debe incluir el nombre del género padre.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: SubGeneroMusicalService.findById(" + id + ") ---");
        SubGeneroMusicalDTO sg = service.findById(id);
        if (sg != null) {
            System.out.println("Subgénero encontrado: " + sg.getNombre());
            System.out.println("  Género Padre: " + sg.getGeneroMusicalNombre());
        } else {
            System.out.println("No se encontró el subgénero con ID: " + id);
        }
    }

    /**
     * Prueba la filtración de subgéneros por el ID del género padre.
     */
    public void testFindByGenero(Long generoId) {
        System.out.println("\n--- Test: SubGeneroMusicalService.findByGenero(ID: " + generoId + ") ---");
        List<SubGeneroMusicalDTO> lista = service.findByGenero(generoId);
        if (lista != null && !lista.isEmpty()) {
            System.out.println("Se encontraron " + lista.size() + " subgéneros para el género " + generoId + ":");
            for (SubGeneroMusicalDTO sg : lista) {
                System.out.println("  - " + sg.getNombre());
            }
        } else {
            System.out.println("No hay subgéneros para el género con ID: " + generoId);
        }
    }
    
    public void testFindAll () {
    	System.out.println("\n--- Test: SubGeneroMusicalService.findAll() ---");
		List<SubGeneroMusicalDTO> lista = service.findAll();
		if (lista != null && !lista.isEmpty()) {
			System.out.println("Se encontraron " + lista.size() + " subgéneros en total:");
			for (SubGeneroMusicalDTO sg : lista) {
				System.out.println("  - " + sg.getNombre() + " (Género: " + sg.getGeneroMusicalNombre() + ")");
			}
		} else {
			System.out.println("No se encontraron subgéneros en el sistema.");
		}
    }

    public static void main(String[] args) {
        SubGeneroMusicalServiceTest test = new SubGeneroMusicalServiceTest();

        // 1. Probar con un ID conocido (ID 2 es Pop Latino)
        test.testFindById(2L);

        // 2. Buscar subgéneros del género Rock (ID 2)
        test.testFindByGenero(2L);
        
        // 3. Encontrar todos
        test.testFindAll();
        
        
    }
}