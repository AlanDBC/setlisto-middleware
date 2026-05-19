package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TipoTicket;

/**
 * Servicio para la gestión y consulta de los tipos de tickets (General, VIP, etc.).
 */
public interface TipoTicketService {

    /**
     * Recupera un tipo de ticket específico por su ID.
     * @param id Identificador del tipo de ticket.
     * @return El objeto TipoTicket o null si no existe.
     */
    public TipoTicket findById(Long id) throws ServiceException;

    /**
     * Obtiene todos los tipos de tickets registrados en el sistema.
     * @return Lista de objetos TipoTicket.
     */
    public List<TipoTicket> findAll() throws ServiceException;
}