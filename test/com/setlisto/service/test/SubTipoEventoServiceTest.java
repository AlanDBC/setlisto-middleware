package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.service.SubTipoEventoService;
import com.setlisto.service.impl.SubTipoEventoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Subtipos de Evento.
 * Valida la jerarquía entre tipos principales (Concierto, Festival) y sus subtipos.
 */
public class SubTipoEventoServiceTest {

    private static final Logger logger = LogManager.getLogger(SubTipoEventoServiceTest.class);
    private SubTipoEventoService subTipoService;

    public SubTipoEventoServiceTest() {
        try {
            // Inicialización del servicio transaccional conforme al middleware [1]
            this.subTipoService = new SubTipoEventoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar SubTipoEventoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de un subtipo por ID.
     * Según datos maestros: 28=Opera, 29=Zarzuela [3].
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio devuelve un DTO con el nombre del tipo padre [1, 4]
            SubTipoEventoDTO subtipo = subTipoService.findById(id);
            if (subtipo != null) {
                logger.info("Subtipo recuperado: {} | Tipo Padre: {}", 
                            subtipo.getNombre(), subtipo.getTipoEventoNombre());
                System.out.println("Resultado: " + subtipo.getNombre() + " (Categoría: " + subtipo.getTipoEventoNombre() + ")");
            } else {
                logger.warn("No se encontró el subtipo con ID: {}", id);
                System.out.println("Subtipo no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de subtipos filtrados por el tipo de evento padre.
     * @param tipoId ID del tipo principal (ej: 1 para Concert, 2 para Festival) [3].
     */
    public void testFindByTipoEvento(Long tipoId) {
        System.out.println("\n--- Ejecutando testFindByTipoEvento para Tipo ID: " + tipoId + " ---");
        try {
            List<SubTipoEventoDTO> lista = subTipoService.findByTipoEvento(tipoId);
            if (lista != null && !lista.isEmpty()) {
                logger.info("Se recuperaron {} subtipos para el tipo {}", lista.size(), tipoId);
                System.out.println("Subtipos encontrados:");
                for (SubTipoEventoDTO s : lista) {
                    System.out.println(" - " + s.getId() + ": " + s.getNombre());
                }
            } else {
                System.out.println("No hay subtipos registrados para este tipo de evento.");
            }
        } catch (Exception e) {
            logger.error("Error recuperando subtipos del tipo {}: {}", tipoId, e.getMessage());
        }
    }

    public static void main(String[] args) {
        SubTipoEventoServiceTest tester = new SubTipoEventoServiceTest();

        // 1. Probar "Opera" (ID 28, pertenece a Musical Show - ID 5) [3]
        tester.testFindById(28L);

        // 2. Probar "Solo Artist" (ID 1, pertenece a Concert - ID 1) [3]
        tester.testFindById(1L);

        // 3. Listar subtipos de "Festival" (ID 2)
        // Debería incluir Outdoor Music Festival, Electronic Festival, etc. [3]
        tester.testFindByTipoEvento(2L);

        System.out.println("\n--- Pruebas de SubTipoEventoService finalizadas ---");
    }
}