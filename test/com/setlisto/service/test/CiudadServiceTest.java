package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.Ciudad;
import com.setlisto.service.CiudadService;
import com.setlisto.service.impl.CiudadServiceImpl;

public class CiudadServiceTest {

    private CiudadService service = null;

    public CiudadServiceTest() {
        this.service = new CiudadServiceImpl();
    }

    /**
     * Prueba el método findByRegionId para verificar que recupera las ciudades de una región
     * @param regionId ID de la región a consultar
     */
    public void testFindByRegionId(Long regionId) {
        System.out.println("--- Test: CiudadService.findByRegionId(" + regionId + ") ---");
        List<Ciudad> ciudades = service.findByRegionId(regionId);
        
        if (ciudades != null && !ciudades.isEmpty()) {
            System.out.println("Se encontraron " + ciudades.size() + " ciudades para la región " + regionId + ":");
            for (Ciudad c : ciudades) {
                System.out.println("  - " + c);
            }
        } else {
            System.out.println("No se encontraron ciudades para la región " + regionId + " o el listado está vacío.");
        }
    }

    public static void main(String[] args) {
        CiudadServiceTest test = new CiudadServiceTest();

        // Ejecutar prueba con un ID de región conocido
        test.testFindByRegionId(5L);
    }
}