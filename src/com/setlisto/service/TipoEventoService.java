package com.setlisto.service;

import java.util.List;

import com.setlisto.model.TipoEvento;

/**
 * Servicio para la gestión y consulta de los tipos de eventos principales.
 */
public interface TipoEventoService {

    /**
     * Recupera un tipo de evento específico por su ID.
     */
    public TipoEvento findById(Long id) throws Exception;

    /**
     * Obtiene el listado completo de tipos de eventos registrados.
     */
    public List<TipoEvento> findAll() throws Exception;
}
