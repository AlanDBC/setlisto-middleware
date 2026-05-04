package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.RegionDAO;
import com.setlisto.model.Region;
import com.setlisto.service.RegionService;

/**
 * Implementación de la lógica de negocio para regiones.
 */
public class RegionServiceImpl implements RegionService {

    private RegionDAO regionDAO = null;

    public RegionServiceImpl() {
        this.regionDAO = new RegionDAO();
    }
    
    @Override
    public List<Region> findByPaisId(Long countryId) {
        // Validamos que el ID del país no sea nulo antes de ir a la BD
        if (countryId == null) {
            return null;
        }
        // Delegamos en el DAO la consulta: SELECT * FROM region WHERE country_id = ?
        return regionDAO.findByPaisId(countryId);
    }
}