package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Ciudad;
import com.setlisto.service.CiudadService;
import com.setlisto.service.impl.CiudadServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Ciudades.
 * Valida la recuperación de ciudades por región según los datos maestros.
 */
public class CiudadServiceTest {

    private static final Logger logger = LogManager.getLogger(CiudadServiceTest.class);
    private CiudadService ciudadService;

    public CiudadServiceTest() {
        try {
            // Inicialización del servicio transaccional [1]
            this.ciudadService = new CiudadServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar CiudadService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de ciudades filtradas por el ID de una región.
     * @param regionId ID de la región (ej: 110 para Andalucía, 120 para Madrid)
     */
    public void testFindByRegionId(Long regionId) {
        System.out.println("\n--- Ejecutando testFindByRegionId para Región ID: " + regionId + " ---");
        try {
            // El servicio invoca al DAO y gestiona la apertura/cierre de conexión [1, 2]
            List<Ciudad> ciudades = ciudadService.findByRegionId(regionId);

            if (ciudades != null && !ciudades.isEmpty()) {
                logger.info("Se encontraron {} ciudades para la región {}", ciudades.size(), regionId);
                System.out.println("Ciudades recuperadas:");
                for (Ciudad c : ciudades) {
                    // Los nombres de ciudades como 'Sevilla' o 'Madrid' provienen de los datos maestros [3]
                    System.out.println(" - ID: " + c.getId() + " | Nombre: " + c.getNombre());
                }
            } else {
                logger.warn("No se encontraron ciudades para la región con ID: {}", regionId);
                System.out.println("No se obtuvieron resultados para esta región.");
            }
        } catch (Exception e) {
            // Captura de errores de SQL o conectividad transaccional
            logger.error("Error en la consulta de ciudades: {}", e.getMessage(), e);
            System.err.println("Excepción capturada: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CiudadServiceTest tester = new CiudadServiceTest();

        // 1. Probar con Andalucía (ID 110 en datos maestros [4])
        // Debería retornar Sevilla, Málaga, Granada, etc. [3]
        tester.testFindByRegionId(110L);

        // 2. Probar con Comunidad de Madrid (ID 120 en datos maestros [4])
        // Debería retornar Madrid, Alcalá de Henares, etc. [3]
        tester.testFindByRegionId(120L);

        // 3. Probar con un ID que no existe
        tester.testFindByRegionId(999L);

        System.out.println("\n--- Pruebas de CiudadService finalizadas ---");
    }
}