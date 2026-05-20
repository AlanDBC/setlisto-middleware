package com.setlisto.service;

import java.sql.Connection;
import java.util.List;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.dao.DataException;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;

/**
 * Servicio para la gestión integral de tickets de eventos.
 */
public interface TicketService {

    /**
     * Recupera un ticket por su id
     * 
     * @param id del ticket
     * @return TicketDTO encontrado
     * @throws ServiceException
     */
    public TicketDTO findById(Long id) throws ServiceException;

    /**
     * Encuentra un ticket por su codigo
     * 
     * @param codigo 
     * @return TicketDTO encontrado
     * @throws ServiceException
     */
    public TicketDTO findByCode(String codigo) throws ServiceException;
    
    
    /**
     * Encuentra tickets por su codigo "contenido en"
     * 
     * @param codigo a buscar
     * @return List<TicketDTO> con caracteres correspondientes
     * @throws DataException
     */
    public List<TicketDTO> findByCodeLike(String code) throws ServiceException;

    /**
     * En cuentra el o los tickets asociados a un pago
     * 
     * @param pagoId
     * @return List<TicketDTO> tickets encontrados
     * @throws ServiceException
     */
    public List<TicketDTO> findByPaymentId(Long pagoId) throws ServiceException;

    /**
     * Recupera todos los tickets comprados por un cliente
     * 
     * @param clienteId
     * @return List<TicketDTO> comprados
     * @throws ServiceException
     */
    public List<TicketDTO> findByClienteId(Long clienteId) throws ServiceException;

    /**
     * Recupera todos los tickets emitidos para un evento
     * 
     * @param musicalEventId
     * @return List<TicketDTO> emitidos
     * @throws ServiceException
     */
    public List<TicketDTO> findByEventoId(Long musicalEventId) throws ServiceException;

    /**
     * Crea un nuevo ticket en el sistema
     * 
     * @param ticket a crear
     * @return TicketDTO creado
     * @throws ServiceException
     */
    public TicketDTO create(TicketDTO ticket) throws ServiceException;

    /**
     * Realiza una busqueda con criterios
     * 
     * @param criteria
     * @param page desde
     * @param size tamaño de pagina
     * @return Results<TicketDTO> (lista) de tickets encontrados
     * @throws ServiceException
     */
    public Results<TicketDTO> findByCriteria(TicketCriteria criteria, int page, int size) throws ServiceException;

    /**
     * Verifica si ya hay un ticket emitido para un asiento de evento especifico
     * 
     * @param plazaEnEventoId
     * @return true si ya existe, false en caso contrario
     * @throws ServiceException
     */
    public boolean existsBySeatOfEventId(Long plazaEnEventoId) throws ServiceException;

    /**
     * Cuenta el total de tickets por evento musical
     * 
     * @param eventoId
     * @return long total de tickets
     * @throws ServiceException
     */
    public long countByEventoId(Long eventoId) throws ServiceException;
}