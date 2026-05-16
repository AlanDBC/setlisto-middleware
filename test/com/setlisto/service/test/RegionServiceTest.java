package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Region;
import com.setlisto.service.RegionService;
import com.setlisto.service.impl.RegionServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Regiones.
 * Valida la recuperación de provincias/comunidades por país.
 */
public class RegionServiceTest {

    private static final Logger logger = LogManager.getLogger(RegionServiceTest.class);
    private RegionService regionService;

    public RegionServiceTest() {
        try {
            // Inicialización del servicio transaccional [3]
            this.regionService = new RegionServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar RegionService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de regiones filtradas por el ID de un país.
     * @param paisId ID del país (ej: 15 para España [1])
     */
    public void testFindByPaisId(Long paisId) {
        System.out.println("\n--- Ejecutando testFindByPaisId para País ID: " + paisId + " ---");
        try {
            // El servicio abre la conexión, desactiva autoCommit y cierra con commit [3]
            List<Region> regiones = regionService.findByPaisId(paisId);

            if (regiones != null && !regiones.isEmpty()) {
                logger.info("Se encontraron {} regiones para el país {}", regiones.size(), paisId);
                System.out.println("Regiones recuperadas:");
                for (Region r : regiones) {
                    // Muestra datos como 'Andalucía' (110) o 'Comunidad de Madrid' (120) [2]
                    System.out.println(" - ID: " + r.getId() + " | Nombre: " + r.getNombre());
                }
            } else {
                logger.warn("No se encontraron regiones para el país con ID: {}", paisId);
                System.out.println("No se obtuvieron resultados para este país.");
            }
        } catch (Exception e) {
            // Captura errores transaccionales relanzados por el ServiceImpl [3]
            logger.error("Error en la consulta de regiones: {}", e.getMessage(), e);
            System.err.println("Excepción capturada: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RegionServiceTest tester = new RegionServiceTest();

        // 1. Probar con España (ID 15 en datos maestros [1])
        // Debería retornar Andalucía, Aragón, Asturias, etc. [2]
        tester.testFindByPaisId(15L);

        // 2. Probar con un ID de país inexistente
        tester.testFindByPaisId(999L);

        System.out.println("\n--- Pruebas de RegionService finalizadas ---");
    }
}