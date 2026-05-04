package com.setlisto.service.impl;

import java.math.BigDecimal;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.dao.PagoDAO;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PagoService;

public class PagoServiceImpl implements PagoService {

    private PagoDAO pagoDAO = null;

    public PagoServiceImpl() {
        this.pagoDAO = new PagoDAO();
    }

    @Override
    public Pago processPayment(Pago pago) {
        // Validación: No permitir duplicados de códigos de transacción si vienen de pasarela
        if (pago.getCodigoTransaccion() != null && pagoDAO.existsByReference(pago.getCodigoTransaccion())) {
            return null; 
        }
        
        // Uso del DAO unificado que devuelve el objeto con ID asignado
        return pagoDAO.create(pago); 
    }

    @Override
    public PagoDTO findById(Long id) {
        return pagoDAO.findById(id); 
    }

    @Override
    public boolean updateStatus(Long paymentId, Long statusId) {
        // Lógica de negocio: Solo actualizar si el pago existe
        if (paymentId != null && statusId != null) {
            return pagoDAO.updateStatus(paymentId, statusId); 
        }
        return false;
    }

    @Override
    public Results<PagoDTO> findByCriteria(PagoCriteria criteria, int from, int pageSize) {
        // Asegura que no se pase un criteria nulo para evitar errores en SQLUtils
        if (criteria == null) {
            criteria = new PagoCriteria();
        }
        return pagoDAO.findByCriteria(criteria, from , pageSize); 
    }

    @Override
    public BigDecimal getTotalApprovedByCustomer(Long customerId) {
        // Retorna la suma de todos los pagos con estado 'APPROVED' (ID 2)
        return pagoDAO.getTotalPagadoByClienteId(customerId);
    }
}