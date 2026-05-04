package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.EstadoPago;
import com.setlisto.service.EstadoPagoService;
import com.setlisto.service.impl.EstadoPagoServiceImpl;

public class EstadoPagoServiceTest {

    private EstadoPagoService service = null;

    public EstadoPagoServiceTest() {
        this.service = new EstadoPagoServiceImpl();
    }

    /**
     * Prueba la recuperación de un estado de pago específico por ID.
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: EstadoPagoService.findById(" + id + ") ---");
        EstadoPago ep = service.findById(id);
        if (ep != null) {
            System.out.println("Estado de pago encontrado: " + ep);
        } else {
            System.out.println("No se encontró el estado de pago con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de todos los estados de pago registrados.
     */
    public void testFindAll() {
        System.out.println("\n--- Test: EstadoPagoService.findAll() ---");
        List<EstadoPago> estados = service.findAll();
        if (estados != null && !estados.isEmpty()) {
            System.out.println("Se encontraron " + estados.size() + " estados de pago:");
            for (EstadoPago ep : estados) {
                System.out.println("  - " + ep);
            }
        } else {
            System.out.println("No se recuperaron estados de pago.");
        }
    }

    public static void main(String[] args) {
        EstadoPagoServiceTest test = new EstadoPagoServiceTest();

        // 1. Probar con ID 1 (Típicamente 'PENDING' según la lógica del sistema)
        test.testFindById(1L);

        // 2. Listar todos los estados (APPROVED, REJECTED, etc.)
        test.testFindAll();
    }
}