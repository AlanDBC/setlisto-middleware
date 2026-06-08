package com.setlisto.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.dao.PlazaDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class PlazaServiceImpl implements PlazaService {

	private static final Logger logger = LogManager.getLogger(PlazaServiceImpl.class.getName());

	private PlazaDAO plazaDAO = null;

	public PlazaServiceImpl() {
		this.plazaDAO = new PlazaDAO();
	}

	@Override
	public PlazaDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			PlazaDTO plaza = plazaDAO.findById(c, id);
			commit = true;
			return plaza;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar plaza con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<PlazaDTO> findByCriteria(PlazaCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<PlazaDTO> resultados = plazaDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar plazas con criteria {}: {}", criteria, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}