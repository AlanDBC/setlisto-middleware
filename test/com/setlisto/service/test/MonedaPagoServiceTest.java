package com.setlisto.service.test;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.MonedaPago;
import com.setlisto.service.MonedaPagoService;
import com.setlisto.service.impl.MonedaPagoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Monedas de Pago.
 * Valida la recuperación de divisas maestras (EUR, USD, GBP) de la tabla payment_currency.
 */
public class MonedaPagoServiceTest {

    private static final Logger logger = LogManager.getLogger(MonedaPagoServiceTest.class);
    private MonedaPagoService monedaService;

    public MonedaPagoServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.monedaService = new MonedaPagoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar MonedaPagoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de una moneda por ID.
     * Según datos maestros: 1=EUR (€), 2=USD ($).
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            // El servicio gestiona la conexión y la transacción internamente [2]
            MonedaPago moneda = monedaService.findById(id);
            if (moneda != null) {
                logger.info("Moneda recuperada: ID={} | Código={} | Símbolo={}", 
                            moneda.getId(), moneda.getCodigo(), moneda.getSimbolo());
                System.out.println("Resultado: " + moneda.getCodigo() + " (" + moneda.getSimbolo() + ")");
            } else {
                logger.warn("No se encontró la moneda con ID: {}", id);
                System.out.println("Moneda no encontrada.");
            }
        } catch (Exception e) {
            logger.error("Excepción en búsqueda por ID {}: {}", id, e.getMessage());
            System.err.println("Error transaccional: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación de todas las monedas registradas en el sistema.
     */
    public void testFindAll() {
        System.out.println("\n--- Ejecutando testFindAll ---");
        try {
            List<MonedaPago> monedas = monedaService.findAll();
            if (monedas != null && !monedas.isEmpty()) {
                logger.info("Se han recuperado {} monedas maestras.", monedas.size());
                System.out.println("Listado de monedas de pago:");
                for (MonedaPago m : monedas) {
                    System.out.println(" - " + m.getId() + ": " + m.getCodigo() + " [" + m.getSimbolo() + "]");
                }
            } else {
                System.out.println("La lista de monedas está vacía.");
            }
        } catch (Exception e) {
            logger.error("Excepción al recuperar todas las monedas: {}", e.getMessage());
            System.err.println("Error al recuperar listado: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        MonedaPagoServiceTest tester = new MonedaPagoServiceTest();

        // 1. Probar "Euro" (ID 1 según datos maestros [1])
        tester.testFindById(1L);

        // 2. Probar id inexistente
        tester.testFindById(43L);

        // 3. Probar recuperación completa de la tabla payment_currency [3]
        tester.testFindAll();

        System.out.println("\n--- Pruebas de MonedaPagoService finalizadas ---");
    }
}