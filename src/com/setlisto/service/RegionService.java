package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Region;

/**
 * Servicio para la gestión y consulta de regiones/provincias.
 */
public interface RegionService {

    /**
     * Obtiene las regiones que pertenecen a un país concreto.
     * @param countryId ID del país (Foreign Key).
     */
    public List<Region> findByPaisId(Long countryId);
}