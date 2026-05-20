package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Ciudad;

/**
 * Servicio para la gestión y consulta de ciudades.
 */
public interface CiudadService {

	/**
	 * Encuentra las ciudades de la region con el id proporcionado
	 * 
	 * @param regionId
	 * @return Lista de ciudades en esa region
	 * @throws ServiceException
	 */
    public List<Ciudad> findByRegionId(Long regionId) throws ServiceException;
}
