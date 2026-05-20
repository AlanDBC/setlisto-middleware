package com.setlisto.service;

import java.util.List;

import com.setlisto.model.MetodoPago;

/**
 * Servicio para la gestión y consulta de los métodos de pago disponibles.
 */
public interface MetodoPagoService {

    /**
     * Recupera un método de pago específico por su ID.
     * 
     * @param id Identificador del método de pago.
     * @return Metodo encontrado
     * @throws ServiceException
     */
    public MetodoPago findById(Long id) throws ServiceException;

    /**
     * Obtiene el listado completo de métodos de pago activos en el sistema.
     * 
     * @return Lista metodos de pago
     * @throws ServiceException
     */
    public List<MetodoPago> findAll() throws ServiceException;
    
}