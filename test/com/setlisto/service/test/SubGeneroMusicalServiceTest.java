package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.service.SubGeneroMusicalService;
import com.setlisto.service.impl.SubGeneroMusicalServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Subgéneros Musicales.
 * Valida la recuperación de subcategorías maestras y su relación con el género padre.
 */
public class SubGeneroMusicalServiceTest {

    private static final Logger logger = LogManager.getLogger(SubGeneroMusicalServiceTest.class);
    private SubGeneroMusicalService subGeneroService;

    public SubGeneroMusicalServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.subGeneroService = new SubGeneroMusicalServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar SubGeneroMusicalService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un subgénero por ID.
     * Según datos maestros: 1=Pop Rock, 124=Death Metal.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio devuelve un DTO que ya contiene el nombre del género musical asociado
            SubGeneroMusicalDTO subgenero = subGeneroService.findById(id);
            if (subgenero != null) {
                logger.info("Subgénero recuperado: {} | Género Padre: {}", 
                            subgenero.getNombre(), subgenero.getGeneroMusicalNombre());
                System.out.println("Resultado: " + subgenero.getNombre() + " (Género: " + subgenero.getGeneroMusicalNombre() + ")");
            } else {
                logger.warn("No se encontró el subgénero con ID: {}", id);
                System.out.println("Subgénero no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de subgéneros filtrados por género musical.
     * @param generoId ID del género padre (ej: 2 para Rock)
     */
    public void testFindByGenero(Long generoId) {
        System.out.println("\n--- Ejecutando testFindByGenero para Género ID: " + generoId + " ---");
        try {
            List<SubGeneroMusicalDTO> lista = subGeneroService.findByGenero(generoId);
            if (lista != null && !lista.isEmpty()) {
                logger.info("Se recuperaron {} subgéneros para el género {}", lista.size(), generoId);
                System.out.println("Subgéneros encontrados:");
                for (SubGeneroMusicalDTO s : lista) {
                    System.out.println(" - " + s.getId() + ": " + s.getNombre());
                }
            } else {
                System.out.println("No hay subgéneros registrados para este género.");
            }
        } catch (Exception e) {
            logger.error("Error recuperando subgéneros del género {}: {}", generoId, e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los subgéneros disponibles.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<SubGeneroMusicalDTO> lista = subGeneroService.findAll();
            if (lista != null && !lista.isEmpty()) {
                logger.info("Se han recuperado {} subgéneros totales.", lista.size());
                System.out.println("Total subgéneros registrados: " + lista.size());
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar listado completo: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        SubGeneroMusicalServiceTest tester = new SubGeneroMusicalServiceTest();

        // 1. Probar "Pop Rock" (ID 1, Género 1)
        tester.testFindById(1L);

        // 2. Probar "Death Metal" (ID 124, Género 12)
        tester.testFindById(124L);

        // 3. Listar subgéneros de "Rock" (ID 2)
        // Debería incluir Classic Rock, Hard Rock, Punk Rock, etc.
        tester.testFindByGenero(2L);

        // 4. Listar todos los subgéneros (147 registros según datos maestros)
        tester.testFindAll();

        System.out.println("\n--- Pruebas de SubGeneroMusicalService finalizadas ---");
    }
}