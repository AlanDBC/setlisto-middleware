package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.EstadoEventoDAO;
import com.setlisto.model.EstadoEvento;
import com.setlisto.service.EstadoEventoService;

public class EstadoEventoServiceImpl implements EstadoEventoService {
    
    private EstadoEventoDAO estadoEventoDAO = null;

    public EstadoEventoServiceImpl() {
        this.estadoEventoDAO = new EstadoEventoDAO();
    }

    @Override
    public EstadoEvento findById(Long id) {
        return estadoEventoDAO.findById(id);
    }

    @Override
    public List<EstadoEvento> findAll() {
        return estadoEventoDAO.findAll();
    }
}