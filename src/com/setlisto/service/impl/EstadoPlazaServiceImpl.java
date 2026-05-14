package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.EstadoPlazaDAO;
import com.setlisto.model.EstadoPlaza;
import com.setlisto.service.EstadoPlazaService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de estados de plaza.
 */
public class EstadoPlazaServiceImpl implements EstadoPlazaService {

	private static final Logger logger = LogManager.getLogger(EstadoPlazaServiceImpl.class.getName());

	private EstadoPlazaDAO estadoPlazaDAO = null;

	public EstadoPlazaServiceImpl() {
		this.estadoPlazaDAO = new EstadoPlazaDAO();
	}

	@Override
	public EstadoPlaza findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EstadoPlaza estado = estadoPlazaDAO.findById(c, id);
			commit = true;
			return estado;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<EstadoPlaza> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<EstadoPlaza> estados = estadoPlazaDAO.findAll(c);
			commit = true;
			return estados;
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}

	}
}
