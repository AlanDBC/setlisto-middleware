package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.EstadoPlazaDAO;
import com.setlisto.model.EstadoPlaza;
import com.setlisto.service.EstadoPlazaService;

/**
 * Implementación del servicio de estados de plaza.
 */
public class EstadoPlazaServiceImpl implements EstadoPlazaService {

    private EstadoPlazaDAO estadoPlazaDAO = null;

    public EstadoPlazaServiceImpl() {
        this.estadoPlazaDAO = new EstadoPlazaDAO();
    }

    @Override
    public EstadoPlaza findById(Long id) {
        return estadoPlazaDAO.findById(id);
    }

    @Override
    public List<EstadoPlaza> findAll() {
        return estadoPlazaDAO.findAll();
    }
}
