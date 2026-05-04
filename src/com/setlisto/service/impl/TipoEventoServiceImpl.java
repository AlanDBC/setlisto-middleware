package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.TipoEventoDAO;
import com.setlisto.model.TipoEvento;
import com.setlisto.service.TipoEventoService;

/**
 * Implementación del servicio de tipos de eventos.
 */
public class TipoEventoServiceImpl implements TipoEventoService {

    private TipoEventoDAO tipoEventoDAO = null;

    public TipoEventoServiceImpl() {
        this.tipoEventoDAO = new TipoEventoDAO();
    }

    @Override
    public TipoEvento findById(Long id) {
        // Delega al DAO para buscar en la tabla event_type
        return tipoEventoDAO.findById(id);
    }

    @Override
    public List<TipoEvento> findAll() {
        // Retorna todos los tipos ordenados por nombre
        return tipoEventoDAO.findAll();
    }
}