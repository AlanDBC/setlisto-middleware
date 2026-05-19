package com.setlisto.service;

import java.util.List;

import com.setlisto.model.SubTipoEventoDTO;

/**
 * Servicio para la gestión y consulta de los subtipos de eventos (ej. Solo Artist, Band Concert).
 */
public interface SubTipoEventoService {

    /**
     * Recupera un subtipo específico por su ID.
     */
    public SubTipoEventoDTO findById(Long id) throws ServiceException;

    /**
     * Recupera todos los subtipos asociados a un tipo de evento padre (ej. todos los de 'Festival').
     * @param tipoEventoId ID del tipo principal (event_type).
     */
    public List<SubTipoEventoDTO> findByTipoEvento(Long tipoEventoId) throws ServiceException;
}