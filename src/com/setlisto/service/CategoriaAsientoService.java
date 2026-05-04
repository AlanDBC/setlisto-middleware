package com.setlisto.service;

import java.util.List;

import com.setlisto.model.CategoriaAsiento;	

/**
 * Servicio encargado de la gestión y consulta de las categorías de asientos disponibles.
 */
public interface CategoriaAsientoService {

	/**
	 * Recupera una categoría específica por su identificador único.
	 * @param id Identificador de la categoría.
	 * @return El objeto CategoriaAsiento o null si no existe.
	 */
	public CategoriaAsiento findById(Long id);

	/**
	 * Obtiene el listado completo de todas las categorías de asientos registradas en el sistema.
	 * @return Lista de objetos CategoriaAsiento.
	 */
	public List<CategoriaAsiento> findAll();
	
}