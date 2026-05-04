package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.CiudadDAO;
import com.setlisto.model.Ciudad;
import com.setlisto.service.CiudadService;

public class CiudadServiceImpl implements CiudadService {

    private CiudadDAO ciudadDAO = null;

    public CiudadServiceImpl() {
        this.ciudadDAO = new CiudadDAO();
    }

    /**
     * Delega la búsqueda de ciudades por región al DAO correspondiente.
     */
    @Override
    public List<Ciudad> findByRegionId(Long regionId) {
        // En el futuro, aquí se podría añadir lógica de caché
        return ciudadDAO.findByRegionId(regionId);
    }
}