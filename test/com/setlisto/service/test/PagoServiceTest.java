package com.setlisto.service.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PagoService;
import com.setlisto.service.impl.PagoServiceImpl;

public class PagoServiceTest {

    private PagoService service = null;

    public PagoServiceTest() {
        this.service = new PagoServiceImpl();
    }

    /**
     * Prueba el registro de un nuevo pago. 
     * Incluye validación contra códigos de transacción duplicados.
     */
    public void testProcessPayment() {
        System.out.println("--- Test: PagoService.processPayment ---");
        Pago nuevoPago = new Pago();
        nuevoPago.setMonto(new BigDecimal("75.00"));
        nuevoPago.setCodigoTransaccion("TXN-SERVICE-TEST-999");
        nuevoPago.setFechaPago(LocalDateTime.now());
        nuevoPago.setClienteId(1L);      // Juan Pérez
        nuevoPago.setEstadoPagoId(1L);   // PENDING
        nuevoPago.setMetodoPagoId(4L);   // Bizum
        nuevoPago.setMonedaId(1L);       // EUR

        Pago procesado = service.processPayment(nuevoPago);
        if (procesado != null && procesado.getId() != null) {
            System.out.println("Pago procesado con éxito e ID: " + procesado.getId());
        } else {
            System.out.println("El pago no se pudo procesar (posible duplicado de transacción).");
        }
    }

    /**
     * Prueba la recuperación de un pago enriquecido con datos de otras tablas.
     */
    public void testFindById(Long id) {
        System.out.println("\n--- Test: PagoService.findById(" + id + ") ---");
        PagoDTO pago = service.findById(id);
        if (pago != null) {
            System.out.println("Pago encontrado: " + pago.getMonto() + " " + pago.getMonedaSimbolo());
            System.out.println("  Cliente: " + pago.getClienteNombre());
            System.out.println("  Método: " + pago.getMetodoPagoNombre());
        } else {
            System.out.println("No se encontró el pago con ID: " + id);
        }
    }

    /**
     * Prueba el cambio de estado de un pago.
     */
    public void testUpdateStatus(Long paymentId, Long statusId) {
        System.out.println("\n--- Test: PagoService.updateStatus ---");
        boolean exito = service.updateStatus(paymentId, statusId);
        System.out.println(exito ? "Estado del pago " + paymentId + " actualizado a " + statusId 
                                 : "Error al actualizar estado.");
    }

    /**
     * Prueba la obtención del total pagado por un cliente en estado 'APPROVED'.
     */
    public void testGetTotalApproved(Long customerId) {
        System.out.println("\n--- Test: PagoService.getTotalApprovedByCustomer(" + customerId + ") ---");
        BigDecimal total = service.getTotalApprovedByCustomer(customerId);
        System.out.println("Total aprobado para el cliente " + customerId + ": " + total + " EUR");
    }

    public void testFindByCriteria() {
		System.out.println("\n--- Test: PagoService.findByCriteria ---");
		PagoCriteria criteria = new PagoCriteria();
		criteria.setMonedaId(1L); // EUR
		Results<PagoDTO> page =  service.findByCriteria(criteria, 1, 10);
		List<PagoDTO> pagos = page.getPage();
		
		for (PagoDTO pago : pagos) {
			System.out.println("Pago encontrado: " + pago.getMonto() + " " + pago.getMonedaSimbolo() 
								+ " - Método: " + pago.getMetodoPagoNombre());
		}
	}
    public static void main(String[] args) {
        PagoServiceTest test = new PagoServiceTest();

        // 1. Procesar un nuevo pago
//        test.testProcessPayment();

        // 2. Buscar un pago existente (según script, el 1L es de Juan Pérez)
//        test.testFindById(1L);

        // 3. Cambiar estado (ej: actualizar el pago 9 de REJECTED a APPROVED)
//        test.testUpdateStatus(9L, 2L);

        // 4. Ver total acumulado aprobado del cliente 1
//        test.testGetTotalApproved(1L);
        
        // 5. Buscar por criterios (ej: pagos en EUR, método Bizum, etc.)
        test.testFindByCriteria();
    }
}