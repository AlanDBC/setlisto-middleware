package com.setlisto.service;

import java.util.List;

import com.setlisto.model.ZonaEvento;

public interface ZonaEventoService {

	/**
	 * Encuentra una zona por su id
	 * 
	 * @param id de la zona
	 * @return Zona encontrada
	 * @throws ServiceException
	 */
	public ZonaEvento findById(Long id) throws ServiceException;

	/**
	 * Encuentra las zonas registradas para un evento
	 * 
	 * @param eventoId 
	 * @return Lista de zonas asociadas a ese evento
	 * @throws ServiceException
	 */
	public List<ZonaEvento> findByEventId(Long eventoId) throws ServiceException;
}
