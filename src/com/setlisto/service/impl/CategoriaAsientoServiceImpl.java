package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.CategoriaAsientoDAO;
import com.setlisto.model.CategoriaAsiento;
import com.setlisto.service.CategoriaAsientoService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio para la gestión de categorías de asientos.
 */
public class CategoriaAsientoServiceImpl implements CategoriaAsientoService {

	private static final Logger logger = LogManager.getLogger(CategoriaAsientoServiceImpl.class.getName());
	private CategoriaAsientoDAO categoriaAsientoDAO = null;

	public CategoriaAsientoServiceImpl() throws Exception {
		this.categoriaAsientoDAO = new CategoriaAsientoDAO();
	}

	/**
	 * Recupera una categoría por su identificador utilizando el DAO [3].
	 */
	@Override
	public CategoriaAsiento findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			CategoriaAsiento categoria = categoriaAsientoDAO.findById(c, id);
			commit = true;		
			return categoria;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Obtiene todas las categorías disponibles en la base de datos [4].
	 */
	@Override
	public List<CategoriaAsiento> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<CategoriaAsiento> categorias = categoriaAsientoDAO.findAll(c);
			commit = true;
			return categorias;
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}