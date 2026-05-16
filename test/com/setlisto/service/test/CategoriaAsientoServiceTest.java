package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.CategoriaAsiento;
import com.setlisto.service.CategoriaAsientoService;
import com.setlisto.service.impl.CategoriaAsientoServiceImpl;

public class CategoriaAsientoServiceTest {

    private static final Logger logger = LogManager.getLogger(CategoriaAsientoServiceTest.class);
    private CategoriaAsientoService categoriaService;

    public CategoriaAsientoServiceTest() {
        try {
            this.categoriaService = new CategoriaAsientoServiceImpl();
        } catch (Exception e) {
            logger.error("Error crítico al inicializar el servicio: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de una categoría específica por ID.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona internamente la conexión y transacción [1]
            CategoriaAsiento categoria = categoriaService.findById(id);
            
            if (categoria != null) {
                logger.info("Resultado exitoso: Categoria encontrada -> [ID: {}, Nombre: {}]", 
                            categoria.getId(), categoria.getNombre());
                System.out.println("Categoría recuperada: " + categoria.getNombre());
            } else {
                logger.warn("No se encontró ninguna categoría con ID: {}", id);
                System.out.println("Categoría no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Error transaccional en testFindById: {}", e.getMessage(), e);
            System.err.println("Error al buscar categoría: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todas las categorías maestras.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            // Recupera la lista completa de la tabla seat_category [1, 2]
            List<CategoriaAsiento> categorias = categoriaService.findAll();
            
            if (categorias != null && !categorias.isEmpty()) {
                logger.info("Se han recuperado {} categorías de la base de datos.", categorias.size());
                System.out.println("Listado de categorías maestras:");
                for (CategoriaAsiento cat : categorias) {
                    System.out.println(" - " + cat.getId() + ": " + cat.getNombre());
                }
            } else {
                logger.warn("La lista de categorías está vacía.");
                System.out.println("No hay categorías registradas.");
            }
        } catch (Exception e) {
            logger.error("Error transaccional en testFindAll: {}", e.getMessage(), e);
            System.err.println("Error al recuperar todas las categorías: " + e.getMessage());
        }
    }

    /**
     * Punto de entrada principal para ejecutar las pruebas.
     */
    public static void main(String[] args) {
        CategoriaAsientoServiceTest tester = new CategoriaAsientoServiceTest();

        // 1. Probar búsqueda por ID (ID 1 es 'General' según datos maestros) [3]
        tester.testFindById(1L);

        // 2. Probar búsqueda por ID inexistente
        tester.testFindById(999L);

        // 3. Probar recuperación de todas las categorías
        tester.testFindAll();
        
        System.out.println("\n--- Pruebas de CategoriaAsientoService finalizadas ---");
    }
}