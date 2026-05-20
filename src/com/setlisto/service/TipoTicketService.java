package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TipoTicket;

/**
 * Servicio para la gestión y consulta de los tipos de tickets (General, VIP, etc.).
 */
public interface TipoTicketService {

    /**
     * Recupera un tipo de ticket por su id
     * 
     * @param id del tipo de ticket
     * @return
     * @throws ServiceException
     */
    public TipoTicket findById(Long id) throws ServiceException;

    /**
     * Obtiene todos los tipos de tickets registrados en el sistema.
     * 
     * @return List<TipoTicket> tipos
     * @throws ServiceException
     */
    public List<TipoTicket> findAll() throws ServiceException;
}