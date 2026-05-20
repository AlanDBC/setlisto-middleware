package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EstadoEvento;


public interface EstadoEventoService {

	/**
	 * Busca un estado por su id
	 * 
	 * @param id del estado
	 * @return Estado encontrado
	 * @throws ServiceException
	 */
    public EstadoEvento findById(Long id) throws ServiceException;

    /**
     * Trae todos los estados de evento dentro de la aplicacion
     * 
     * @return Lista de estados
     * @throws ServiceException
     */
    public List<EstadoEvento> findAll() throws ServiceException;
}