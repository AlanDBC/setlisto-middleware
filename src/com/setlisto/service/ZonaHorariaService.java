package com.setlisto.service;

import java.util.List;

import com.setlisto.model.ZonaHoraria;

public interface ZonaHorariaService {
	
	/**
	 * Recupera una zona horaria por su id
	 * 
	 * @param id de la zona horaria
	 * @return ZonaHoraria encontrada
	 * @throws ServiceException
	 */
	public ZonaHoraria findById (Long id) throws ServiceException;
	
	/**
	 * Devuelve todas las zonas horarias disponibles
	 * 
	 * @return List<ZonaHoraria> zonas
	 * @throws ServiceException
	 */
	public List<ZonaHoraria> findAll() throws ServiceException;
}
