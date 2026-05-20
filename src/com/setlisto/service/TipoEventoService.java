package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TipoEvento;

/**
 * Servicio para la gestión y consulta de los tipos de eventos principales.
 */
public interface TipoEventoService {

    /**
     * Recupera un tipo de evento específico por su ID.
     * 
     * @param id del tipo de evento
     * @return
     * @throws ServiceException
     */
    public TipoEvento findById(Long id) throws ServiceException;

    /**
     * Obtiene el listado completo de tipos de eventos registrados.
     * 
     * @return List<TipoEvento> tipos de evento
     * @throws ServiceException
     */
    public List<TipoEvento> findAll() throws ServiceException;
}
