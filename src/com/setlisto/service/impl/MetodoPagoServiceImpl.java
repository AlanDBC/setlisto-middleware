package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.MetodoPagoDAO;
import com.setlisto.model.MetodoPago;
import com.setlisto.service.MetodoPagoService;

/**
 * Implementación del servicio de métodos de pago.
 */
public class MetodoPagoServiceImpl implements MetodoPagoService {

    private MetodoPagoDAO metodoPagoDAO = null;

    public MetodoPagoServiceImpl() {
        this.metodoPagoDAO = new MetodoPagoDAO();
    }

    /**
     * Recupera el método de pago utilizando el DAO.
     */
    @Override
    public MetodoPago findById(Long id) {
        return metodoPagoDAO.findById(id);
    }

    /**
     * Obtiene todos los métodos de pago (VISA, PayPal, Bizum, etc.) definidos en la tabla payment_method.
     */
    @Override
    public List<MetodoPago> findAll() {
        return metodoPagoDAO.findAll();
    }
}