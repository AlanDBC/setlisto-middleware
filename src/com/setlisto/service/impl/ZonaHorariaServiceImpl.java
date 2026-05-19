package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.ZonaHorariaDAO;
import com.setlisto.model.ZonaHoraria;
import com.setlisto.service.ServiceException;
import com.setlisto.service.ZonaHorariaService;
import com.setlisto.utils.JDBCUtils;

public class ZonaHorariaServiceImpl implements ZonaHorariaService {

	private static final Logger logger = LogManager.getLogger(ZonaHorariaServiceImpl.class.getName());
	private ZonaHorariaDAO zonaHorariaDAO = null;

	public ZonaHorariaServiceImpl() {
		this.zonaHorariaDAO = new ZonaHorariaDAO();
	}

	@Override
	public ZonaHoraria findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			ZonaHoraria zona = zonaHorariaDAO.findById(c, id);
			commit = true;
			return zona;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar zona horaria con Id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<ZonaHoraria> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<ZonaHoraria> zonas = zonaHorariaDAO.findAll(c);
			commit = true;
			return zonas;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todas las zonas horarias: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todas: {}", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
