package com.setlisto.service;

import java.util.List;

import com.setlisto.model.SubTipoEventoDTO;

/**
 * Servicio para la gestión y consulta de los subtipos de eventos (ej. Solo Artist, Band Concert).
 */
public interface SubTipoEventoService {

    /**
     * Recupera un subtipo de evento por su id
     * 
     * @param id
     * @return Subtipo encontrado
     * @throws ServiceException
     */
    public SubTipoEventoDTO findById(Long id) throws ServiceException;

    /**
     * Recupera todos los subtipos asociados a un tipo de evento padre
     * 
     * @param tipoEventoId
     * @return Lista de subtipos asociados
     * @throws ServiceException
     */
    public List<SubTipoEventoDTO> findByTipoEvento(Long tipoEventoId) throws ServiceException;
}