package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.EstadoEventoDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.EstadoEvento;
import com.setlisto.service.EstadoEventoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class EstadoEventoServiceImpl implements EstadoEventoService {

	private static final Logger logger = LogManager.getLogger(EstadoEventoServiceImpl.class.getName());
	private EstadoEventoDAO estadoEventoDAO = null;

	public EstadoEventoServiceImpl() {
		this.estadoEventoDAO = new EstadoEventoDAO();
	}

	@Override
	public EstadoEvento findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EstadoEvento encontrado = estadoEventoDAO.findById(c, id);
			commit = true;
			return encontrado;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar estado con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<EstadoEvento> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<EstadoEvento> estados =estadoEventoDAO.findAll(c);
			commit = true;
			return estados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los estados: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos{}: {}", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}