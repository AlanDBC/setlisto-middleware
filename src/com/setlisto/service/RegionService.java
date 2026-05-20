package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Region;

/**
 * Servicio para la gestión y consulta de regiones/provincias.
 */
public interface RegionService {

    /**
     * Busca las regiones para un pais especifico
     * 
     * @param paisId
     * @return Lista de regiones del pais
     * @throws ServiceException
     */
    public List<Region> findByPaisId(Long paisId) throws ServiceException;
}