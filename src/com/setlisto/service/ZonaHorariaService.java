package com.setlisto.service;

import java.util.List;

import com.setlisto.model.ZonaHoraria;

public interface ZonaHorariaService {
	/**
	 * Devuelve la zona horaria con el id indicado
	 */
	public ZonaHoraria findById (Long id) throws Exception;
	
	/**
	 * Devuelve todas las zonas horarias disponibles
	 */
	public List<ZonaHoraria> findAll() throws Exception;
}
