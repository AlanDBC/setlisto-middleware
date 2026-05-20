package com.setlisto.service;

import java.util.List;

import com.setlisto.model.CategoriaAsiento;	

/**
 * Servicio encargado de la gestión y consulta de las categorías de asientos disponibles.
 */
public interface CategoriaPlazaService {

	/**
	 * Encuentra una categoria por su id
	 * 
	 * @param id
	 * @return Categoria encontrada
	 * @throws ServiceException
	 */
	public CategoriaAsiento findById(Long id) throws ServiceException;

	/**
	 * Trae todas las categorias que existen
	 * 
	 * @return Lista de categorias
	 * @throws ServiceException
	 */
	public List<CategoriaAsiento> findAll() throws ServiceException;
	
}