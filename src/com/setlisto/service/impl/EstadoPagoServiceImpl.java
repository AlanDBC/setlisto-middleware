package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.EstadoPagoDAO;
import com.setlisto.model.EstadoPago;
import com.setlisto.service.EstadoPagoService;

/**
 * Implementación del servicio de estados de pago.
 */
public class EstadoPagoServiceImpl implements EstadoPagoService {

    private EstadoPagoDAO estadoPagoDAO = null;

    public EstadoPagoServiceImpl() {
        this.estadoPagoDAO = new EstadoPagoDAO();
    }

    /**
     * Recupera el estado de pago delegando al DAO.
     */
    @Override
    public EstadoPago findById(Long id) {
        return estadoPagoDAO.findById(id);
    }

    /**
     * Recupera todos los estados de pago (PENDING, APPROVED, REJECTED, etc.).
     */
    @Override
    public List<EstadoPago> findAll() {
        return estadoPagoDAO.findAll();
    }
}