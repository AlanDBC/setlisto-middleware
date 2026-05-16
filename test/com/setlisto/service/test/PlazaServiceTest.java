package com.setlisto.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaService;
import com.setlisto.service.impl.PlazaServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Plazas (Asientos físicos).
 * Valida la configuración de asientos en los recintos del sistema.
 */
public class PlazaServiceTest {

    private static final Logger logger = LogManager.getLogger(PlazaServiceTest.class);
    private PlazaService plazaService;

    public PlazaServiceTest() {
        try {
            // Inicialización del servicio transaccional conforme al middleware [4]
            this.plazaService = new PlazaServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar PlazaService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de la información técnica de una plaza por ID [1].
     * Según datos de prueba, el ID 1 es un asiento de 'Pista' en el WiZink Center [2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y el cierre transaccional [4]
            PlazaDTO plaza = plazaService.findById(id);
            if (plaza != null) {
                logger.info("Plaza recuperada: Recinto='{}' | Fila={} | Número={}", 
                            plaza.getLugarNombre(), plaza.getFila(), plaza.getNumeroAsiento());
                System.out.println("Recinto: " + plaza.getLugarNombre());
                System.out.println("Ubicación: Fila " + plaza.getFila() + ", Asiento " + plaza.getNumeroAsiento());
                System.out.println("Categoría: " + plaza.getCategoriaNombre());
            } else {
                System.out.println("Asiento físico no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error en testFindById para plaza {}: {}", id, e.getMessage());
            System.err.println("Excepción capturada: " + e.getMessage());
        }
    }

    /**
     * Prueba la búsqueda de plazas aplicando filtros por recinto o categoría [1].
     */
    public void testFindByCriteria(Long siteId) {
        System.out.println("\n--- Ejecutando testFindByCriteria para Recinto ID: " + siteId + " ---");
        try {
            PlazaCriteria criteria = new PlazaCriteria();
            // Suponiendo setters en criteria para filtrar por siteId [5]
             criteria.setLugarId(siteId); 

            // Búsqueda paginada: primera página con 10 resultados
            Results<PlazaDTO> resultados = plazaService.findByCriteria(criteria, 0, 10);
            
            if (resultados != null && !resultados.getPage().isEmpty()) {
                System.out.println("Total de asientos encontrados en el recinto: " + resultados.getTotal());
                for (PlazaDTO p : resultados.getPage()) {
                    System.out.println(" - ID: " + p.getId() + " | Fila: " + p.getFila() + " | Nº: " + p.getNumeroAsiento() + " [" + p.getCategoriaNombre() + "]");
                }
            } else {
                System.out.println("No se encontraron asientos configurados para este recinto.");
            }
        } catch (Exception e) {
            logger.error("Error en búsqueda por criterios: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        PlazaServiceTest tester = new PlazaServiceTest();

        // 1. Probar un asiento conocido (ID 1: WiZink Center, Pista) [2]
        tester.testFindById(1L);

        // 2. Probar un asiento en el Palau Sant Jordi (ID 3: Fila 112, Nº 45) [2]
        tester.testFindById(3L);

        // 3. Probar búsqueda de asientos para el recinto WiZink Center (ID 1) [3]
        tester.testFindByCriteria(1L);

        System.out.println("\n--- Pruebas de PlazaService finalizadas ---");
    }
}