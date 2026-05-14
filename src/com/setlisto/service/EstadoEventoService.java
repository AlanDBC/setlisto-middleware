package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EstadoEvento;

/**
 * Servicio para la gestión y consulta de los estados de un evento.
 */
public interface EstadoEventoService {
    /**
     * Recupera un estado específico por su ID.
     */
    public EstadoEvento findById(Long id) throws Exception;

    /**
     * Obtiene el listado completo de estados registrados.
     */
    public List<EstadoEvento> findAll() throws Exception;
}