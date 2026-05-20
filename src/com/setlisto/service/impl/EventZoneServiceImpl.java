package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.ZonaEventoDAO;
import com.setlisto.model.ZonaEvento;
import com.setlisto.service.ZonaEventoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class EventZoneServiceImpl implements ZonaEventoService {

	private static final Logger logger = LogManager.getLogger(EventZoneServiceImpl.class.getName());
	private ZonaEventoDAO eventZoneDAO = new ZonaEventoDAO();

	@Override
	public ZonaEvento findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			ZonaEvento zone = eventZoneDAO.findById(c, id);
			commit = true;
			return zone;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar zona con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando zona por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<ZonaEvento> findByEventId(Long musicalEventId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<ZonaEvento> zones = eventZoneDAO.findByEventId(c, musicalEventId);
			commit = true;
			return zones;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar zona por evento con id {}: {}", musicalEventId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando zonas de evento {}: {}", musicalEventId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
