package com.setlisto.service;

import java.util.List;

import com.setlisto.model.MonedaPago;

/**
 * Servicio para la gestión y consulta de las monedas aceptadas en los pagos.
 */
public interface MonedaPagoService {

    /**
     * Recupera una moneda específica por su ID.
     * 
     * @param id de la moneda 
     * @return Moneda de pago encontrada
     * @throws ServiceException
     */
    public MonedaPago findById(Long id) throws ServiceException;

    /**
     * Obtiene el listado completo de monedas registradas en el sistema.
     * 
     * @return Lista monedas de pago
     * @throws ServiceException
     */
    public List<MonedaPago> findAll() throws ServiceException;
}