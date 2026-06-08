package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.PaisDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.Pais;
import com.setlisto.service.PaisService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de países.
 */
public class PaisServiceImpl implements PaisService {

	private static final Logger logger = LogManager.getLogger(PaisServiceImpl.class.getName());

	private PaisDAO paisDAO = null;

	public PaisServiceImpl() {
		this.paisDAO = new PaisDAO();
	}

	@Override
	public Pais findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Pais pais = paisDAO.findById(c, id);
			commit = true;
			return pais;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar pais con id {} : {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id{}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<Pais> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Pais> paises = paisDAO.findAll(c);
			commit = true;
			return paises;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los paises: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}