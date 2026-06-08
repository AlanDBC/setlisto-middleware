package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.SubGeneroMusicalDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.service.ServiceException;
import com.setlisto.service.SubGeneroMusicalService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de subgéneros musicales.
 */
public class SubGeneroMusicalServiceImpl implements SubGeneroMusicalService {
	
	private static final Logger logger = LogManager.getLogger(SubGeneroMusicalServiceImpl.class.getName());

	private SubGeneroMusicalDAO subGeneroDAO = null;

	public SubGeneroMusicalServiceImpl() {
		this.subGeneroDAO = new SubGeneroMusicalDAO();
	}

	@Override
	public SubGeneroMusicalDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			SubGeneroMusicalDTO subgenero = subGeneroDAO.findById(c, id);
			commit = true;
			return subgenero;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar subgenero musical con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando subgenero con id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<SubGeneroMusicalDTO> findByGenero(Long generoId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
		List<SubGeneroMusicalDTO> subgeneros = subGeneroDAO.findByGeneroId(c, generoId);
		commit = true;
		return subgeneros;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar subgenero musical por genero con id {}: {}", generoId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando subgenero de genero musical con id {}: {}", generoId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}	
	}

	@Override
	public List<SubGeneroMusicalDTO> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
		List<SubGeneroMusicalDTO> subgeneros = subGeneroDAO.findAll(c);
		commit = true;
		return subgeneros;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los subgenero musicales: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos los subgenero: {}", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}