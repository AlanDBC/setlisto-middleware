package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.GeneroMusical;
import com.setlisto.service.GeneroMusicalService;
import com.setlisto.service.impl.GeneroMusicalServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Géneros Musicales.
 * Valida la recuperación de géneros maestros (Pop, Rock, Metal, etc.).
 */
public class GeneroMusicalServiceTest {

    private static final Logger logger = LogManager.getLogger(GeneroMusicalServiceTest.class);
    private GeneroMusicalService generoService;

    public GeneroMusicalServiceTest() {
        try {
            // Inicialización del servicio transaccional [3]
            this.generoService = new GeneroMusicalServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar GeneroMusicalService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un género específico por ID.
     * Según datos maestros: 1=Pop, 2=Rock, 12=Metal [2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y el commit/rollback internamente [3]
            GeneroMusical genero = generoService.findById(id);
            if (genero != null) {
                logger.info("Género recuperado: ID={} | Nombre={}", genero.getId(), genero.getNombre());
                System.out.println("Resultado: " + genero.getNombre());
            } else {
                logger.warn("No se encontró el género con ID: {}", id);
                System.out.println("Género no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los géneros musicales ordenados por nombre [1].
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<GeneroMusical> generos = generoService.findAll();
            if (generos != null && !generos.isEmpty()) {
                logger.info("Se han recuperado {} géneros musicales.", generos.size());
                System.out.println("Lista de géneros disponibles:");
                for (GeneroMusical g : generos) {
                    System.out.println(" - " + g.getId() + ": " + g.getNombre());
                }
            } else {
                System.out.println("La lista de géneros está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los géneros: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        GeneroMusicalServiceTest tester = new GeneroMusicalServiceTest();

        // 1. Probar "Pop" (ID 1)
        tester.testFindById(1L);

        // 2. Probar id inexistente
        tester.testFindById(77L);

        // 3. Probar recuperación completa de la tabla musical_genre
        tester.testFindAll();

        System.out.println("\n--- Pruebas de GeneroMusicalService finalizadas ---");
    }
}