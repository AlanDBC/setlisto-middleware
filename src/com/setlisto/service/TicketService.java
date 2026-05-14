package com.setlisto.service;

import java.util.List;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;

/**
 * Servicio para la gestión integral de tickets de eventos.
 */
public interface TicketService {

    /**
     * Recupera un ticket específico por su identificador único.
     */
    public TicketDTO findById(Long id) throws Exception;

    /**
     * Busca un ticket mediante su código único (ej. código QR).
     */
    public TicketDTO findByCode(String code) throws Exception;

    /**
     * Obtiene el listado de tickets asociados a un pago específico.
     */
    public List<TicketDTO> findByPaymentId(Long paymentId) throws Exception;

    /**
     * Recupera todos los tickets comprados por un cliente.
     */
    public List<TicketDTO> findByClienteId(Long clienteId) throws Exception;

    /**
     * Obtiene todos los tickets emitidos para un evento musical.
     */
    public List<TicketDTO> findByEventoId(Long musicalEventId) throws Exception;

    /**
     * Registra un nuevo ticket en el sistema.
     * @return El objeto TicketDTO con su ID asignado.
     */
    public TicketDTO create(TicketDTO ticket) throws Exception;

    /**
     * Realiza búsquedas dinámicas de tickets aplicando filtros.
     */
    public Results<TicketDTO> findByCriteria(TicketCriteria criteria, int page, int size) throws Exception;

    /**
     * Verifica si una plaza en un evento ya tiene un ticket emitido.
     */
    public boolean existsBySeatOfEventId(Long seatOfMusicalEventId) throws Exception;

    /**
     * Cuenta el total de tickets emitidos para un evento concreto.
     */
    public long countByEventoId(Long musicalEventId) throws Exception;
}