package com.setlisto.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.ZonaHoraria;
import com.setlisto.service.ZonaHorariaService;
import com.setlisto.service.impl.ZonaHorariaServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Zonas Horarias.
 * Valida la recuperación de identificadores IANA de la tabla time_zone.
 */
public class ZonaHorariaServiceTest {

    private static final Logger logger = LogManager.getLogger(ZonaHorariaServiceTest.class);
    private ZonaHorariaService zonaHorariaService;

    public ZonaHorariaServiceTest() {
        try {
            // Inicialización del servicio [2]
            this.zonaHorariaService = new ZonaHorariaServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar ZonaHorariaService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de una zona horaria por ID.
     * Según datos maestros: 1=UTC, 3=Europe/Madrid [4].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            ZonaHoraria zona = zonaHorariaService.findById(id);
            if (zona != null) {
                logger.info("Zona horaria recuperada: {}", zona.getNombre());
                System.out.println("Nombre IANA: " + zona.getNombre());
            } else {
                System.out.println("Zona horaria no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Error buscando zona {}: {}", id, e.getMessage());
        }
    }

    public static void main(String[] args) {
        ZonaHorariaServiceTest tester = new ZonaHorariaServiceTest();

        // 1. Probar UTC (ID 1)
        tester.testFindById(1L);

        // 2. Probar Madrid (ID 3)
        tester.testFindById(3L);

        System.out.println("\n--- Pruebas de ZonaHorariaService finalizadas ---");
    }
}