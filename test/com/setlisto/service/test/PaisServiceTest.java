package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Pais;
import com.setlisto.service.PaisService;
import com.setlisto.service.impl.PaisServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Países.
 * Valida la recuperación de países maestros de la tabla country.
 */
public class PaisServiceTest {

    private static final Logger logger = LogManager.getLogger(PaisServiceTest.class);
    private PaisService paisService;

    public PaisServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.paisService = new PaisServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar PaisService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un país por su ID.
     * Según datos maestros: 15=España, 18=Francia, 23=Italia.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y la transacción internamente
            Pais pais = paisService.findById(id);
            if (pais != null) {
                logger.info("País recuperado: ID={} | Nombre={}", pais.getId(), pais.getNombre());
                System.out.println("Resultado: " + pais.getNombre());
            } else {
                logger.warn("No se encontró el país con ID: {}", id);
                System.out.println("País no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todos los países ordenados alfabéticamente.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<Pais> paises = paisService.findAll();
            if (paises != null && !paises.isEmpty()) {
                logger.info("Se han recuperado {} países maestros.", paises.size());
                System.out.println("Listado de países disponibles:");
                for (Pais p : paises) {
                    System.out.println(" - " + p.getId() + ": " + p.getNombre());
                }
            } else {
                System.out.println("La lista de países está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todos los países: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PaisServiceTest tester = new PaisServiceTest();

        // 1. Probar "España" (ID 15 según datos maestros)
        tester.testFindById(15L);

        // 2. Probar id inexistente
        tester.testFindById(99L);

        // 3. Probar recuperación completa de la tabla country (IDs 1 al 45)
        tester.testFindAll();

        System.out.println("\n--- Pruebas de PaisService finalizadas ---");
    }
}