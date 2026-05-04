package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.dao.TicketDAO;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;
import com.setlisto.service.TicketService;

/**
 * Implementación de la lógica de negocio para la gestión de tickets.
 */
public class TicketServiceImpl implements TicketService {

    private TicketDAO ticketDAO = null;

    public TicketServiceImpl() {
        this.ticketDAO = new TicketDAO();
    }

    @Override
    public TicketDTO findById(Long id) {
        return ticketDAO.findById(id);
    }

    @Override
    public TicketDTO findByCode(String code) {
        return ticketDAO.findByCode(code);
    }

    @Override
    public List<TicketDTO> findByPaymentId(Long paymentId) {
        return ticketDAO.findByPaymentId(paymentId);
    }

    @Override
    public List<TicketDTO> findByClienteId(Long clienteId) {
        return ticketDAO.findByClienteId(clienteId);
    }

    @Override
    public List<TicketDTO> findByEventoId(Long musicalEventId) {
        return ticketDAO.findByEventoId(musicalEventId);
    }

    @Override
    public TicketDTO create(TicketDTO ticket) {
        // El DAO devuelve el ID generado al insertar
        Long generatedId = ticketDAO.create(ticket);
        if (generatedId != null) {
            ticket.setId(generatedId);
            return ticket;
        }
        return null;
    }

    @Override
    public Results<TicketDTO> findByCriteria(TicketCriteria criteria, int from, int pageSize) {
        // Evita errores de nulidad al construir la consulta SQL dinámica
        if (criteria == null) {
            criteria = new TicketCriteria();
        }
        return ticketDAO.findByCriteria(criteria, from, pageSize);
    }

    @Override
    public boolean existsBySeatOfEventId(Long seatOfMusicalEventId) {
        return ticketDAO.existsBySeatOfEventId(seatOfMusicalEventId);
    }

    @Override
    public long countByEventoId(Long musicalEventId) {
        return ticketDAO.countByEventoId(musicalEventId);
    }
}