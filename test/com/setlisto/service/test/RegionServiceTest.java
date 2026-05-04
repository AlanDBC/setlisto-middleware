package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.Region;
import com.setlisto.service.RegionService;
import com.setlisto.service.impl.RegionServiceImpl;

public class RegionServiceTest {

    private RegionService service = null;

    public RegionServiceTest() {
        this.service = new RegionServiceImpl();
    }

    /**
     * Prueba la obtención de regiones filtradas por el identificador del país.
     * @param paisId ID del país (Ej: 15 para España).
     */
    public void testFindByPaisId(Long paisId) {
        System.out.println("--- Iniciando prueba: findByPaisId(" + paisId + ") ---");
        
        List<Region> regiones = service.findByPaisId(paisId);
        
        if (regiones != null && !regiones.isEmpty()) {
            System.out.println("Éxito: Se han encontrado " + regiones.size() + " regiones.");
            // Listamos las primeras para verificar la integridad
            for (Region r : regiones) {
                // Gracias a AbstractValueObject veremos los datos por reflexión
                System.out.println("  -> " + r);
            }
        } else {
            System.out.println("Fallo: No se encontraron regiones para el país con ID " + paisId);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        RegionServiceTest test = new RegionServiceTest();

        // 1. Probar con España (ID 15 según Tablas Maestras)
        // Debería devolver 19 regiones (Andalucía, Aragón, etc.)
        test.testFindByPaisId(15L);

        // 2. Probar con un ID inexistente
        test.testFindByPaisId(999L);
        
        // 3. Probar con nulo (validación de seguridad)
        test.testFindByPaisId(null);
    }
}