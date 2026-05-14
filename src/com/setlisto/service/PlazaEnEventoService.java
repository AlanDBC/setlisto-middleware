package com.setlisto.service;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión dinámica de la disponibilidad de asientos por evento.
 */
public interface PlazaEnEventoService {

    /**
     * Recupera el detalle de una plaza específica en un evento por su ID.
     */
    public PlazaEnEventoDTO findById(Long id) throws Exception;

    /**
     * Busca plazas filtrando por evento o por estado (AVAILABLE, SOLD, DISABLED).
     */
    public Results<PlazaEnEventoDTO> findByCriteria(PlazaEnEventoCriteria criteria, int page, int size) throws Exception;

    /**
     * Comprueba si una plaza específica está marcada como AVAILABLE (ID 1).
     */
    public boolean isSeatAvailable(Long plazaEnEventoId) throws Exception;

    /**
     * Actualiza el estado de una plaza (fundamental para el proceso de compra).
     */
    public boolean updateStatus(Long plazaEnEventoId, Long statusId) throws Exception;

    /**
     * Obtiene el número total de asientos disponibles para un evento concreto.
     */
    public int getAvailableCount(Long eventId) throws Exception;
}