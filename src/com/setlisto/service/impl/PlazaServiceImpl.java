package com.setlisto.service.impl;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.dao.PlazaDAO;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaService;

/**
 * Implementación del servicio de plazas físicas.
 */
public class PlazaServiceImpl implements PlazaService {

    private PlazaDAO plazaDAO = null;

    public PlazaServiceImpl() {
        this.plazaDAO = new PlazaDAO();
    }

    @Override
    public PlazaDTO findById(Long id) {
        // Recupera el DTO con el nombre del recinto y la categoría ya resueltos
        return plazaDAO.findById(id);
    }

    @Override
    public Results<PlazaDTO> findByCriteria(PlazaCriteria criteria, int from, int pageSize) {
        // Si no hay criterios, se inicializan para evitar errores en la lógica del DAO
        if (criteria == null) {
            criteria = new PlazaCriteria();
        }
        return plazaDAO.findByCriteria(criteria, from, pageSize);
    }
}