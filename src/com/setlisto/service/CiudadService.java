package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Ciudad;

/**
 * Servicio para la gestión y consulta de ciudades.
 */
public interface CiudadService {

    /**
     * Obtiene el listado de ciudades pertenecientes a una región específica.
     * @param regionId Identificador de la región (provincia/estado).
     * @return Lista de objetos Ciudad.
     */
    public List<Ciudad> findByRegionId(Long regionId) throws ServiceException;
}
