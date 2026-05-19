package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EstadoPago;

/**
 * Servicio para la gestión y consulta de los estados de pago.
 */
public interface EstadoPagoService {

    /**
     * Recupera un estado de pago específico por su ID.
     * @param id Identificador del estado de pago.
     * @return El objeto EstadoPago o null si no existe.
     */
    public EstadoPago findById(Long id) throws ServiceException;

    /**
     * Obtiene todos los estados de pago registrados en el sistema.
     * @return Lista de objetos EstadoPago.
     */
    public List<EstadoPago> findAll() throws ServiceException;
}