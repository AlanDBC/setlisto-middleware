package com.setlisto.service.test;

import java.util.List;

import com.setlisto.model.MonedaPago;
import com.setlisto.service.MonedaPagoService;
import com.setlisto.service.impl.MonedaPagoServiceImpl;

public class MonedaPagoServiceTest {

    private MonedaPagoService service = null;

    public MonedaPagoServiceTest() {
        this.service = new MonedaPagoServiceImpl();
    }

    /**
     * Prueba la recuperación de una moneda específica por ID.
     * Según los datos maestros, el ID 1 corresponde a 'EUR' (€).
     */
    public void testFindById(Long id) {
        System.out.println("--- Test: MonedaPagoService.findById(" + id + ") ---");
        MonedaPago mp = service.findById(id);
        if (mp != null) {
            System.out.println("Moneda encontrada: " + mp.getCodigo() + " (" + mp.getSimbolo() + ")");
        } else {
            System.out.println("No se encontró la moneda con ID: " + id);
        }
    }

    /**
     * Prueba la obtención de todas las monedas aceptadas (EUR, USD, GBP).
     */
    public void testFindAll() {
        System.out.println("\n--- Test: MonedaPagoService.findAll() ---");
        List<MonedaPago> monedas = service.findAll();
        if (monedas != null && !monedas.isEmpty()) {
            System.out.println("Se encontraron " + monedas.size() + " monedas:");
            for (MonedaPago mp : monedas) {
                System.out.println("  - " + mp.getCodigo() + " [Símbolo: " + mp.getSimbolo() + "]");
            }
        } else {
            System.out.println("No se recuperaron monedas de la base de datos.");
        }
    }

    public static void main(String[] args) {
        MonedaPagoServiceTest test = new MonedaPagoServiceTest();

        // 1. Probar con ID 1 (Euro)
        test.testFindById(1L);

        // 2. Listar todas las monedas del sistema
        test.testFindAll();
    }
}