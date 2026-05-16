package com.setlisto.service.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.service.PagoService;
import com.setlisto.service.impl.PagoServiceImpl;

/**
 * Clase ejecutable para testear el servicio de Pagos.
 * Maneja el registro de transacciones financieras y consultas de estados.
 */
public class PagoServiceTest {

    private static final Logger logger = LogManager.getLogger(PagoServiceTest.class);
    private PagoService pagoService;

    public PagoServiceTest() {
        try {
            // Inicialización del servicio transaccional
            this.pagoService = new PagoServiceImpl();
        } catch (Exception e) {
            logger.error("Error al inicializar PagoService: {}", e.getMessage());
        }
    }

    /**
     * Prueba el procesamiento y registro de un nuevo pago.
     */
    public void testProcessPayment() {
        System.out.println("\n--- Ejecutando testProcessPayment ---");
        try {
            Pago nuevoPago = new Pago();
            nuevoPago.setMonto(new BigDecimal("99.99"));
            nuevoPago.setCodigoTransaccion("TXN-TEST-999");
            nuevoPago.setFechaPago(LocalDateTime.now());
            nuevoPago.setClienteId(1L); // Juan Pérez
            nuevoPago.setEstadoPagoId(1L); // Pendiente
            nuevoPago.setMetodoPagoId(1L); // VISA
            nuevoPago.setMonedaId(1L); // EUR

            // El servicio abre transacción y persiste el pago
            Pago procesado = pagoService.processPayment(nuevoPago);
            
            if (procesado != null && procesado.getId() != null) {
                logger.info("Pago procesado con éxito. ID: {}", procesado.getId());
                System.out.println("Pago registrado: " + procesado.getCodigoTransaccion() + " (ID: " + procesado.getId() + ")");
            }
        } catch (Exception e) {
            logger.error("Error al procesar el pago: {}", e.getMessage());
            System.err.println("Fallo transaccional en el pago: " + e.getMessage());
        }
    }

    /**
     * Prueba la recuperación detallada de un pago por ID.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Ejecutando testFindById para ID: " + id + " ---");
        try {
            PagoDTO pago = pagoService.findById(id);
            if (pago != null) {
                logger.info("Pago recuperado: {} de {}€", pago.getCodigoTransaccion(), pago.getMonto());
                System.out.println("Transacción: " + pago.getCodigoTransaccion());
                System.out.println("Monto: " + pago.getMonto() + " (Estado ID: " + pago.getEstadoPagoId() + ")");
            } else {
                System.out.println("Pago no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error buscando pago {}: {}", id, e.getMessage());
        }
    }

    /**
     * Prueba la actualización del estado de un pago.
     */
    public void testUpdateStatus(Long paymentId, Long newStatusId) {
        System.out.println("\n--- Ejecutando testUpdateStatus para Pago ID: " + paymentId + " ---");
        try {
            // Cambiamos, por ejemplo, de Pendiente (1) a Aprovado (2)
            boolean actualizado = pagoService.updateStatus(paymentId, newStatusId);
            if (actualizado) {
                logger.info("Estado del pago {} actualizado a {}", paymentId, newStatusId);
                System.out.println("Estado actualizado correctamente.");
            }
        } catch (Exception e) {
            logger.error("Error actualizando estado del pago: {}", e.getMessage());
        }
    }

    /**
     * Prueba el cálculo del total aprobado para un cliente.
     */
    public void testGetTotalApproved(Long customerId) {
        System.out.println("\n--- Ejecutando getTotalApprovedByCustomer para Cliente ID: " + customerId + " ---");
        try {
            BigDecimal total = pagoService.getTotalApprovedByCustomer(customerId);
            logger.info("Total aprobado para cliente {}: {}", customerId, total);
            System.out.println("Inversión total del cliente: " + total + "€");
        } catch (Exception e) {
            logger.error("Error calculando total: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        PagoServiceTest tester = new PagoServiceTest();

        // 1. Probar búsqueda de un pago real (ID 1: TXN-AITANA-001) [1]
        tester.testFindById(1L);

        // 2. Probar el proceso de un nuevo pago
        tester.testProcessPayment();

        // 3. Probar actualización de estado (de Rechazado a Aprobado en ID 9) [1]
        tester.testUpdateStatus(9L, 2L);

        // 4. Probar total acumulado de Juan Pérez (ID 1) [1, 3]
        tester.testGetTotalApproved(1L);

        System.out.println("\n--- Pruebas de PagoService finalizadas ---");
    }
}