package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.MonedaPagoDAO;
import com.setlisto.model.MonedaPago;
import com.setlisto.service.MonedaPagoService;

/**
 * Implementación del servicio de monedas de pago.
 */
public class MonedaPagoServiceImpl implements MonedaPagoService {

    private MonedaPagoDAO monedaPagoDAO = null;

    public MonedaPagoServiceImpl() {
        this.monedaPagoDAO = new MonedaPagoDAO();
    }

    /**
     * Recupera la moneda delegando al DAO, que consulta la tabla payment_currency.
     */
    @Override
    public MonedaPago findById(Long id) {
        return monedaPagoDAO.findById(id);
    }

    /**
     * Obtiene todas las monedas registradas (EUR, USD, GBP, etc.).
     */
    @Override
    public List<MonedaPago> findAll() {
        return monedaPagoDAO.findAll();
    }
}