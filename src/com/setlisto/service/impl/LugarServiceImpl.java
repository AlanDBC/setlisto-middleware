package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.dao.LugarDAO;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;
import com.setlisto.service.LugarService;

/**
 * Implementación del servicio de gestión de lugares.
 */
public class LugarServiceImpl implements LugarService {

    private LugarDAO lugarDAO = null;

    public LugarServiceImpl() {
        this.lugarDAO = new LugarDAO();
    }

    @Override
    public LugarDTO findById(Long id) {
        return lugarDAO.findById(id);
    }

    @Override
    public List<LugarDTO> findAll() {
        return lugarDAO.findAll();
    }

    @Override
    public Results<LugarDTO> findByCriteria(LugarCriteria criteria, int from, int pageSize) {
        // Se asegura de no pasar un criteria nulo al DAO
        if (criteria == null) {
            criteria = new LugarCriteria();
        }
        return lugarDAO.findByCriteria(criteria, from, pageSize);
    }

    @Override
    public Lugar create(Lugar lugar) {
        if (lugar.getNombre() != null && lugar.getCiudadId() != null) {

            return lugarDAO.create(lugar);
        }        
        return null;
    }

    @Override
    public void update(Lugar lugar) {
        if (lugar.getId() != null) {
            lugarDAO.update(lugar);
        }
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            lugarDAO.delete(id);
        }
    }
}