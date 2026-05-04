package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.CategoriaAsientoDAO;
import com.setlisto.model.CategoriaAsiento;
import com.setlisto.service.CategoriaAsientoService;

/**
 * Implementación del servicio para la gestión de categorías de asientos.
 */
public class CategoriaAsientoServiceImpl implements CategoriaAsientoService {

	private CategoriaAsientoDAO categoriaAsientoDAO = null;

	/**
	 * El constructor inicializa el DAO correspondiente para la persistencia de datos [2].
	 */
	public CategoriaAsientoServiceImpl() {
		this.categoriaAsientoDAO = new CategoriaAsientoDAO();
	}

	/**
	 * Recupera una categoría por su identificador utilizando el DAO [3].
	 */
	@Override
	public CategoriaAsiento findById(Long id) {
		return categoriaAsientoDAO.findById(id);
	}

	/**
	 * Obtiene todas las categorías disponibles en la base de datos [4].
	 */
	@Override
	public List<CategoriaAsiento> findAll() {
		return categoriaAsientoDAO.findAll();
	}
}