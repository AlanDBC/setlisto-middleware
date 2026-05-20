package com.setlisto.service;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión dinámica de la disponibilidad de asientos por evento.
 */
public interface PlazaEnEventoService {

    /**
     * Recupera el detalle de una plaza de un evento especifico
     * 
     * @param id de la plaza
     * @return Plaza encontrada
     * @throws ServiceException
     */
    public PlazaEnEventoDTO findById(Long id) throws ServiceException;

    /**
     * Busca plazas de un evento especifico por criterios
     * 
     * @param criteria
     * @param page desde 
     * @param size tamaño de pagina
     * @return Lista (Results<>) de plazas correspondientes a los criterios 
     * @throws ServiceException
     */
    public Results<PlazaEnEventoDTO> findByCriteria(PlazaEnEventoCriteria criteria, int page, int size) throws ServiceException;

    /**
     * Verifica si la plaza esta disponible dentro de ese evento
     * 
     * @param plazaEnEventoId
     * @return true si esta disponible, false en caso contrario
     * @throws ServiceException
     */
    public boolean isSeatAvailable(Long plazaEnEventoId) throws ServiceException;

    /**
     * Actualiza el estado de una plaza especifica de un evento
     * 
     * @param plazaEnEventoId
     * @param estadoId
     * @return true si se actualizo, false en caso contrario
     * @throws ServiceException
     */
    public boolean updateStatus(Long plazaEnEventoId, Long estadoId) throws ServiceException;

    /**
     * Obtiene el numero de plazas disponibles dento de un evento especifico 
     * 
     * @param eventId
     * @return Total de plazas disponibles
     * @throws ServiceException
     */
    public int getAvailableCount(Long eventId) throws ServiceException;
}