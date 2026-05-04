package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.EstadoEvento;
import com.setlisto.service.EstadoEventoService;
import com.setlisto.service.impl.EstadoEventoServiceImpl;

public class EstadoEventoServiceTest {

    private EstadoEventoService service = null;

    public EstadoEventoServiceTest() {
        this.service = new EstadoEventoServiceImpl();
    }

    /**
     * Prueba la recuperación de todos los estados maestros (BORRADOR, PROGRAMADO, etc.).
     */
    public void testFindAll() {
        System.out.println("--- Probando findAll() ---");
        List<EstadoEvento> estados = service.findAll();
        
        if (estados != null && !estados.isEmpty()) {
            System.out.println("Se encontraron " + estados.size() + " estados:");
            for (EstadoEvento e : estados) {
                // El método toString() usa reflexión de AbstractValueObject [3]
                System.out.println("  - " + e.getNombre() + " (ID: " + e.getId() + ")");
            }
        } else {
            System.out.println("No se encontraron estados en la base de datos.");
        }
        System.out.println();
    }

    /**
     * Prueba la recuperación de un estado específico por su ID.
     */
    public void testFindById(Long id) {
        System.out.println("--- Probando findById(" + id + ") ---");
        EstadoEvento estado = service.findById(id);
        
        if (estado != null) {
            System.out.println("Estado encontrado: " + estado);
        } else {
            System.out.println("Estado con ID " + id + " no encontrado.");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        EstadoEventoServiceTest test = new EstadoEventoServiceTest();
        
        // Ejecución de pruebas sencillitas
        test.testFindAll();
        
        // Probamos con el ID 1 que corresponde a 'BORRADOR' según tu SQL
        test.testFindById(1L);
        
        // Probamos con un ID que no exista
        test.testFindById(99L);
    }
}