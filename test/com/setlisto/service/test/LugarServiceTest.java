package com.setlisto.service.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.service.LugarService;
import com.setlisto.service.impl.LugarServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Lugares (Recintos).
 * Incluye pruebas de creación, búsqueda y eliminación.
 */
public class LugarServiceTest {

    private static final Logger logger = LogManager.getLogger(LugarServiceTest.class);
    private LugarService lugarService;

    public LugarServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.lugarService = new LugarServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar LugarService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la creación de un nuevo recinto.
     */
    public void testCreate() {
        System.out.println("\n--- Ejecutando testCreate ---");
        try {
            Lugar nuevoLugar = new Lugar();
            nuevoLugar.setNombre("Estadio de Prueba");
            nuevoLugar.setDireccion("Calle Ficticia 123");
            nuevoLugar.setCiudadId(437L); // Madrid 
            nuevoLugar.setIdZonaHoraria(3l); // Europe|Madrid

            // El servicio abre transacción, inserta y hace commit
            Lugar creado = lugarService.create(nuevoLugar);
            
            if (creado != null && creado.getId() != null) {
                logger.info("Lugar creado exitosamente con ID: {}", creado.getId());
                System.out.println("Lugar creado: " + creado.getNombre() + " (ID: " + creado.getId() + ")");
                
                // Limpieza: Borramos el lugar de prueba para mantener la BD limpia
                testDelete(creado.getId());
            }
        } catch (Exception e) {
            logger.error("Error al crear el lugar: {}", e.getMessage());
            System.err.println("Excepción en creación: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un lugar por ID.
     * Según datos maestros: 1=WiZink Center, 2=Palau Sant Jordi [2].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            LugarDTO lugar = lugarService.findById(id);
            if (lugar != null) {
                logger.info("Lugar recuperado: {} en {}", lugar.getNombre(), lugar.getCiudadNombre());
                System.out.println("Recinto: " + lugar.getNombre());
                System.out.println("Dirección: " + lugar.getDireccion());
                System.out.println("Ciudad: " + lugar.getCiudadNombre());
            } else {
                System.out.println("No se encontró el recinto.");
            }
        } catch (Exception e) {
            logger.error("Error buscando lugar {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba la eliminación de un lugar.
     */
    public void testDelete(Long id) {
        System.out.println("\n--- Ejecutando testDelete para ID: " + id + " ---");
        try {
            lugarService.delete(id);
            logger.info("Lugar con ID {} eliminado correctamente.", id);
            System.out.println("Eliminación confirmada.");
        } catch (Exception e) {
            logger.error("Error al eliminar lugar {}: {}", id, e.getMessage());
            System.err.println("No se pudo eliminar: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LugarServiceTest tester = new LugarServiceTest();

        // 1. Probar búsqueda de un recinto real (ID 1: WiZink Center [2])
        tester.testFindById(1L);

        // 2. Probar creación y eliminación de un recinto de prueba
        tester.testCreate();

        System.out.println("\n--- Pruebas de LugarService finalizadas ---");
    }
}