package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.TipoTicketDAO;
import com.setlisto.model.TipoTicket;
import com.setlisto.service.TipoTicketService;

/**
 * Implementación del servicio para la gestión de tipos de tickets.
 */
public class TipoTicketServiceImpl implements TipoTicketService {

    private TipoTicketDAO tipoTicketDAO = null;

    /**
     * El constructor inicializa el DAO correspondiente.
     */
    public TipoTicketServiceImpl() {
        this.tipoTicketDAO = new TipoTicketDAO();
    }

    /**
     * Recupera un tipo de ticket delegando al DAO.
     */
    @Override
    public TipoTicket findById(Long id) {
        return tipoTicketDAO.findById(id);
    }

    /**
     * Obtiene todos los tipos de tickets (General, VIP, Pista, Grada) definidos.
     */
    @Override
    public List<TipoTicket> findAll() {
        return tipoTicketDAO.findAll();
    }
}