package com.setlisto.service;

import java.math.BigDecimal;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión de transacciones financieras y estados de pago.
 */
public interface PagoService {

    /**
     * Procesa y registra un nuevo pago en el sistema.
     * Devuelve el objeto enriquecido con su ID generado.
     */
    public Pago processPayment(Pago pago) throws Exception;

    /**
     * Recupera la información detallada de un pago por su ID.
     */
    public PagoDTO findById(Long id) throws Exception;

    /**
     * Actualiza el estado de un pago (ej. de PENDIENTE a APROBADO).
     */
    public boolean updateStatus(Long paymentId, Long statusId) throws Exception;

    /**
     * Realiza búsquedas dinámicas de pagos por cliente, moneda, fechas o métodos.
     */
    public Results<PagoDTO> findByCriteria(PagoCriteria criteria, int page, int size) throws Exception;

    /**
     * Calcula el monto total que un cliente ha pagado en reservas aprobadas.
     */
    public BigDecimal getTotalApprovedByCustomer(Long customerId) throws Exception;
}