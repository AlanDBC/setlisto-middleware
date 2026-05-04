package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.MetodoPago;
import com.setlisto.service.MetodoPagoService;
import com.setlisto.service.impl.MetodoPagoServiceImpl;

public class MetodoPagoServiceTest {

    private MetodoPagoService service = null;

    public MetodoPagoServiceTest() {
        this.service = new MetodoPagoServiceImpl();
    }

    /**
     * Prueba la recuperación de un método de pago específico por ID.
     * Según los datos maestros, el ID 3 corresponde a 'PayPal'
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: MetodoPagoService.findById(" + id + ") ---");
        MetodoPago mp = service.findById(id);
        if (mp != null) {
            System.out.println("Método encontrado: " + mp.getNombre());
        } else {
            System.out.println("No se encontró el método de pago con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de todos los métodos de pago registrados (VISA, PayPal, Bizum, etc.).
     */
    public void testFindAll() {
        System.out.println("\n--- Test: MetodoPagoService.findAll() ---");
        List<MetodoPago> metodos = service.findAll();
        if (metodos != null && !metodos.isEmpty()) {
            System.out.println("Se encontraron " + metodos.size() + " métodos de pago:");
            for (MetodoPago mp : metodos) {
                System.out.println("  - " + mp.getNombre());
            }
        } else {
            System.out.println("No se recuperaron métodos de pago.");
        }
    }

    public static void main(String[] args) {
        MetodoPagoServiceTest test = new MetodoPagoServiceTest();

        // 1. Probar con un ID conocido (ejemplo: 3L para PayPal)
        test.testFindById(3L);

        // 2. Listar todos los métodos maestros
        test.testFindAll();
    }
}