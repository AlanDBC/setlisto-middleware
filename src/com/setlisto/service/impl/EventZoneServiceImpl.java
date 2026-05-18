package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.EventZoneDAO;
import com.setlisto.model.EventZone;
import com.setlisto.service.EventZoneService;
import com.setlisto.utils.JDBCUtils;

public class EventZoneServiceImpl implements EventZoneService {

	private static final Logger logger = LogManager.getLogger(EventZoneServiceImpl.class.getName());
	private EventZoneDAO eventZoneDAO = new EventZoneDAO();

	@Override
	public EventZone findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EventZone zone = eventZoneDAO.findById(c, id);
			commit = true;
			return zone;
		} catch (Exception e) {
			logger.error("Buscando zona por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<EventZone> findByEventId(Long musicalEventId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<EventZone> zones = eventZoneDAO.findByEventId(c, musicalEventId);
			commit = true;
			return zones;
		} catch (Exception e) {
			logger.error("Buscando zonas de evento {}: {}", musicalEventId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
